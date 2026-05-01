const {onCall, HttpsError} = require("firebase-functions/v2/https");
const {defineSecret} = require("firebase-functions/params");
const {OpenAI} = require("openai");

const openaiApiKey = defineSecret("OPENAI_API_KEY");

exports.getOpenAIResponse = onCall(
    {
      secrets: [openaiApiKey],
      region: "us-central1",
    },
    async (request) => {
      // 1. Log detallado para ver qué llega al servidor
      console.log("--- Nueva Petición ---");
      console.log("Autenticación presente:", !!request.auth);
      if (request.auth) {
        console.log("UID del usuario:", request.auth.uid);
      }

      // 2. Verificación de seguridad
      if (!request.auth) {
        throw new HttpsError(
            "unauthenticated",
            "El servidor no detectó tu sesión. " +
            "Intenta cerrar sesión y volver a entrar.",
        );
      }

      const prompt = request.data.prompt;
      if (!prompt) {
        throw new HttpsError("invalid-argument", "El prompt es obligatorio.");
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
              content: "Eres un tutor experto. Responde siempre en: \n" +
                "Español: [res]\nQuechua: [res]\nAimara: [res]",
            },
            {role: "user", content: prompt},
          ],
        });

        return {response: completion.choices[0].message.content};
      } catch (error) {
        console.error("OpenAI Error:", error);
        throw new HttpsError("internal", "Error al conectar con la IA.");
      }
    },
);
