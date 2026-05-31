const {onCall, HttpsError} = require("firebase-functions/v2/https");
const {defineSecret} = require("firebase-functions/params");
const {OpenAI} = require("openai");
const admin = require("firebase-admin");
const path = require("path");
const os = require("os");
const fs = require("fs-extra");

// Inicializamos Firebase Admin para acceder a Storage
admin.initializeApp();

const openaiApiKey = defineSecret("OPENAI_API_KEY");

// --- 1. FLUJO DE TEXTO ---
exports.getOpenAIResponse = onCall(
    {
      secrets: [openaiApiKey],
      region: "us-central1",
    },
    async (request) => {
      console.log("--- Nueva Petición de Texto ---");

      if (!request.auth) {
        throw new HttpsError("unauthenticated", "Sesión no detectada.");
      }

      const prompt = request.data.prompt;

      if (!prompt) {
        throw new HttpsError(
            "invalid-argument",
            "El prompt es obligatorio.",
        );
      }

      const openai = new OpenAI({
        apiKey: openaiApiKey.value(),
      });

      try {
        const completion = await openai.chat.completions.create({
          model: "gpt-4o-mini",
          messages: [
            {
              role: "system",
              content:
                "Eres un tutor experto. Responde siempre en:\n" +
                "Español: [res]\n" +
                "Quechua: [res]\n" +
                "Aimara: [res]",
            },
            {
              role: "user",
              content: prompt,
            },
          ],
        });

        return {
          response: completion.choices[0].message.content,
        };
      } catch (error) {
        console.error("OpenAI Error:", error);

        throw new HttpsError(
            "internal",
            "Error al conectar con la IA.",
        );
      }
    },
);

// --- 2. FLUJO DE AUDIO ---
exports.processAudioMessage = onCall(
    {
      secrets: [openaiApiKey],
      region: "us-central1",
      timeoutSeconds: 300,
    },
    async (request) => {
      console.log(
          "Nueva petición de audio. Usuario:",
          request.auth ? request.auth.uid : "ANONIMO",
      );

      // Ahora recibimos audioPath
      const audioPath = request.data.audioPath;

      if (!audioPath) {
        throw new HttpsError(
            "invalid-argument",
            "audioPath es requerido.",
        );
      }

      const openai = new OpenAI({
        apiKey: openaiApiKey.value(),
      });

      const tempFilePath = path.join(
          os.tmpdir(),
          `audio_${Date.now()}.m4a`,
      );

      try {
        // A. Descargar audio desde Firebase Storage
        console.log("Descargando archivo:", audioPath);

        const bucket = admin.storage().bucket();

        await bucket.file(audioPath).download({
          destination: tempFilePath,
        });

        // B. Transcribir Audio usando Whisper
        console.log("Enviando a Whisper...");

        const transcription =
          await openai.audio.transcriptions.create({
            file: fs.createReadStream(tempFilePath),
            model: "whisper-1",
            language: "es",
          });

        const userText = transcription.text;

        console.log("Transcripción exitosa:", userText);

        // C. Procesar texto con GPT
        console.log("Generando respuesta y feedback con GPT...");

        const completion =
          await openai.chat.completions.create({
            model: "gpt-4o-mini",
            messages: [
              {
                role: "system",
                content:
                  "Eres un tutor experto en lenguas andinas. " +
                  "El usuario te envió un mensaje de voz.\n" +
                  "Analiza la transcripción y responde " +
                  "siguiendo estrictamente este formato JSON:\n" +
                  "{\n" +
                  "  \"response\": " +
                  "\"Español: [res]\\n" +
                  "Quechua: [res]\\n" +
                  "Aimara: [res]\",\n" +
                  "  \"feedback\": " +
                  "\"Retroalimentación sobre tu pronunciación " +
                  "(según transcripción), gramática y " +
                  "sugerencias de mejora.\"\n" +
                  "}",
              },
              {
                role: "user",
                content: userText,
              },
            ],
            response_format: {
              type: "json_object",
            },
          });

        const aiResult = JSON.parse(
            completion.choices[0].message.content,
        );

        return {
          transcription: userText,
          response: aiResult.response,
          feedback: aiResult.feedback,
        };
      } catch (error) {
        console.error("Critical Audio Error:", error);

        throw new HttpsError(
            "internal",
            `No pudimos procesar tu audio: ${error.message}`,
        );
      } finally {
        if (fs.existsSync(tempFilePath)) {
          await fs.remove(tempFilePath);
        }
      }
    },
);
