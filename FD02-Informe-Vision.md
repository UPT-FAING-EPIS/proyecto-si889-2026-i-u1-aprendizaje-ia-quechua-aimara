<center>

[comment]: <img src="./media/media/image1.png" style="width:1.088in;height:1.46256in" alt="escudo.png" />

![./media/media/image1.png](./media/logo-upt.png)

**UNIVERSIDAD PRIVADA DE TACNA**

**FACULTAD DE INGENIERIA**

**Escuela Profesional de Ingeniería de Sistemas**

**Proyecto *Sistema móvil con inteligencia artificial para el aprendizaje de quechua y aimara***

Curso: *Patrones de Software*

Docente: *Mag. Ing. Patrick Cuadros Quiroga*

Integrantes:

***Mamani Estaña, Junior (2022075474)***
***Serrano Ibañez, Nestor Juice Yomar (2022075474)***

**Tacna – Perú**

***2026***

** **
</center>
<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

|CONTROL DE VERSIONES||||||
| :-: | :- | :- | :- | :- | :- |
|Versión|Hecha por|Revisada por|Aprobada por|Fecha|Motivo|
|1.0|JME, NSI|PCQ|PCQ|04/04/2026|Versión 1 [cite: 619, 623]|

**Sistema *Sistema móvil con inteligencia artificial para el aprendizaje de quechua y aimara* [cite: 611]**

**Documento de Visión [cite: 621]**

**Versión *1.0* [cite: 622]**
**

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

|CONTROL DE VERSIONES||||||
| :-: | :- | :- | :- | :- | :- |
|Versión|Hecha por|Revisada por|Aprobada por|Fecha|Motivo|
[cite_start]|1.0|JME, NSI|PCQ|PCQ|04/04/2026|Versión 1 [cite: 619, 623]|


<div style="page-break-after: always; visibility: hidden">\pagebreak</div>


**INDICE GENERAL**
#
[1. Introducción](#_Toc52661346)
1.1 Propósito
1.2 Alcance
1.3 Definiciones, Siglas y Abreviaturas
1.4 Referencias
1.5 Visión General

[2. Posicionamiento](#_Toc52661347)
2.1 Oportunidad de negocio
2.2 Definición del problema

[3. Descripción de los interesados y usuarios](#_Toc52661348)
3.1 Resumen de los interesados
3.2 Resumen de los usuarios
3.3 Entorno de usuario
3.4 Perfiles de los interesados
3.5 Perfiles de los Usuarios
3.6 Necesidades de los interesados y usuarios

[4. Vista General del Producto](#_Toc52661349)
4.1 Perspectiva del producto
4.2 Resumen de capacidades
4.3 Suposiciones y dependencias
4.4 Costos y precios
4.5 Licenciamiento e instalación

[5. Características del producto](#_Toc52661350)

[6. Restricciones](#_Toc52661351)

[7. Rangos de calidad](#_Toc52661352)

[8. Precedencia y Prioridad](#_Toc52661353)

[9. Otros requerimientos del producto](#_Toc52661354)

[CONCLUSIONES](#_Toc52661355)

[RECOMENDACIONES](#_Toc52661356)

[BIBLIOGRAFIA](#_Toc52661357)

[WEBGRAFIA](#_Toc52661358)


<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

**<u>Informe de Visión</u>**

1. <span id="_Toc52661346" class="anchor"></span>**Introducción**

    **1.1 Propósito**
    [cite_start]Desarrollar un sistema móvil con inteligencia artificial que permita mejorar el aprendizaje de los idiomas quechua y aimara mediante una plataforma interactiva, accesible y personalizada[cite: 655]. [cite_start]Se busca optimizar el proceso incorporando ejercicios dinámicos, chatbot educativo y reconocimiento de voz para fomentar la preservación de lenguas originarias[cite: 656].

    **1.2 Alcance**
    [cite_start]El sistema incluye módulos estructurados de aprendizaje, ejercicios interactivos, evaluaciones automáticas, seguimiento del progreso, chatbot con IA y reconocimiento de voz para pronunciación[cite: 658]. [cite_start]Ofrece contenidos personalizados según el nivel de conocimiento de cada usuario[cite: 659].

    **1.3 Definiciones, Siglas y Abreviaturas**
    * [cite_start]**SRS**: Documento de Especificación de Requisitos del Software[cite: 661].
    * **MVVM**: Model View ViewModel[cite: 662].
    * [cite_start]**Firebase**: Plataforma de servicios en la nube para desarrollo de aplicaciones[cite: 663].

    **1.4 Referencias**
    * [cite_start]Documentos de registros del proyecto[cite: 665, 666].
    * Informe de Factibilidad del Sistema[cite: 392].

    **1.5 Visión General**
    La visión es desarrollar una aplicación móvil innovadora que facilite el aprendizaje de lenguas originarias mediante IA, promoviendo la preservación cultural y el acceso a la educación digital intuitiva y eficiente[cite: 668, 669].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

2. <span id="_Toc52661347" class="anchor"></span>**Posicionamiento**

    **2.1 Oportunidad de negocio**
    Existe una escasez de aplicaciones enfocadas en el aprendizaje de quechua y aimara con tecnologías modernas, lo que representa una oportunidad para una solución innovadora que combine educación e inteligencia artificial[cite: 672, 673].

    **2.2 Definición del problema**
    El aprendizaje de estas lenguas presenta limitaciones por la falta de herramientas digitales accesibles e interactivas[cite: 675]. Los métodos tradicionales no se adaptan a las necesidades actuales y faltan plataformas que utilicen IA para mejorar habilidades como la pronunciación[cite: 676].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

3. <span id="_Toc52661348" class="anchor"></span>**Descripción de los interesados y usuarios**

    **3.1 Resumen de los interesados**
    * [cite_start]**Nestor Serrano Ibañez**: Jefe del Proyecto / Programador / Analista[cite: 679].
    * [cite_start]**Junior Mamani Estaña**: Programador / Analista[cite: 679].
    * **Usuario del sistema**: Usuario final del aplicativo[cite: 679].

    **3.2 Resumen de los usuarios**
    * [cite_start]**Usuario general**: Persona interesada en aprender quechua o aimara[cite: 681].
    * [cite_start]**Administrador**: Encargado de gestionar contenido, usuarios y reportes[cite: 681].

    **3.3 Entorno de usuario**
    [cite_start]Aplicación móvil Android desarrollada en Kotlin para dispositivos con acceso a internet[cite: 683]. [cite_start]Los usuarios generales interactúan con módulos y herramientas de IA, mientras el administrador gestiona el sistema[cite: 684, 685].

    **3.4 Perfiles de los interesados**
    * **Equipo de Desarrollo**: Responsables de la construcción, integración de IA, pruebas y mantenimiento del sistema[cite: 688].
    * [cite_start]**Usuario del Sistema**: Persona externa interesada en un aprendizaje efectivo con facilidad de uso y contenido confiable[cite: 687].

    **3.5 Perfiles de los Usuarios**
    * [cite_start]**Perfil Usuario General**: Principal beneficiario encargado de interactuar con los módulos de aprendizaje y herramientas de IA para su progreso continuo[cite: 690].

    **3.6 Necesidades de los interesados y usuarios**
    * Plataforma segura y fácil de usar[cite: 692].
    * [cite_start]Aprendizaje interactivo y dinámico[cite: 693].
    * [cite_start]Seguimiento del progreso y mejora en la pronunciación por voz[cite: 694, 695].
    * Acceso a contenido educativo confiable[cite: 696].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

4. <span id="_Toc52661349" class="anchor"></span>**Vista General del Producto**

    **4.1 Perspectiva del producto**
    Aplicación móvil educativa que integra IA para ofrecer una experiencia personalizada, combinando procesamiento de lenguaje natural y reconocimiento de voz[cite: 699, 700].

    **4.2 Resumen de capacidades**
    * [cite_start]Registro y autenticación de usuarios[cite: 702].
    * [cite_start]Módulos estructurados (vocabulario, frases, gramática) y ejercicios interactivos[cite: 703, 704].
    * Chatbot con IA y reconocimiento de voz para evaluación de pronunciación[cite: 705, 706].
    * [cite_start]Seguimiento de progreso y generación de reportes[cite: 707, 708].

    **4.3 Suposiciones y dependencias**
    * [cite_start]Uso de dispositivos Android compatibles con acceso a internet[cite: 710].
    * Dependencia de servicios Firebase y APIs de inteligencia artificial externas[cite: 711].
    * [cite_start]Validación previa del contenido educativo por especialistas[cite: 712].

    **4.4 Costos y precios**
    * **Costo Total del Proyecto**: S/. [cite_start]5,993.00[cite: 714].
    * **Costo Mensual Operativo**: S/. 1,997.67[cite: 714].

    **4.5 Licenciamiento e instalación**
    Instalación mediante archivo APK en dispositivos Android (API 24/7.0 o superior)[cite: 466]. El licenciamiento se ajustará a términos de servicios en la nube y APIs de terceros (GPT-4o-mini, Google Cloud)[cite: 493].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

5. <span id="_Toc52661350" class="anchor"></span>**Características del producto**
* [cite_start]Registro y autenticación segura de usuarios[cite: 716].
* [cite_start]Módulos de aprendizaje y ejercicios de selección múltiple, completar oraciones y traducción[cite: 717, 718].
* Chatbot educativo con IA para práctica conversacional[cite: 719].
* [cite_start]Reconocimiento de voz con retroalimentación inmediata sobre la pronunciación[cite: 720].
* [cite_start]Interfaz amigable desarrollada con Jetpack Compose[cite: 723].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

6. <span id="_Toc52661351" class="anchor"></span>**Restricciones**
* Necesidad obligatoria de internet para funciones de IA y sincronización[cite: 725].
* [cite_start]Limitado a dispositivos Android 8.0 o superior[cite: 726].
* [cite_start]Presupuesto definido que condiciona el volumen de uso de APIs externas[cite: 728].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

7. <span id="_Toc52661352" class="anchor"></span>**Rangos de Calidad**
* **Disponibilidad**: Mínimo 99% mensual[cite: 730].
* [cite_start]**Rendimiento**: Tiempo de respuesta ≤ 3 segundos en operaciones comunes[cite: 731].
* [cite_start]**Seguridad**: Autenticación y almacenamiento seguro de datos personales[cite: 733].
* **Usabilidad**: Interfaz intuitiva para usuarios con distintos niveles tecnológicos[cite: 732].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

8. <span id="_Toc52661353" class="anchor"></span>**Precedencia y Prioridad**
1. Registro, autenticación y gestión segura de accesos (Prioridad Alta)[cite: 736, 737].
2. Funcionamiento correcto de módulos de aprendizaje y ejercicios (Prioridad Alta)[cite: 738, 739].
3. Implementación de chatbot con IA y reconocimiento de voz (Prioridad Media)[cite: 740, 741].
4. Estabilidad, rapidez y escalabilidad del sistema (Prioridad Media)[cite: 743, 744].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

9. <span id="_Toc52661354" class="anchor"></span>**Otros requerimientos del producto**
* [cite_start]**Estándares Legales**: Cumplimiento de la Ley N° 29733 (Protección de Datos Personales)[cite: 746].
* [cite_start]**Estándares de Comunicación**: Mensajes claros, instrucciones simples y retroalimentación comprensible[cite: 747].
* **Estándares de Usabilidad**: Navegación intuitiva para personas con poca experiencia tecnológica[cite: 748].
* [cite_start]**Estándares de Calidad**: Estabilidad y bajo margen de errores durante el uso[cite: 750].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

<span id="_Toc52661355" class="anchor"></span>**CONCLUSIONES**
* [cite_start]El sistema es una solución innovadora que utiliza IA para optimizar el proceso educativo de lenguas originarias[cite: 752].
* El proyecto cumple un objetivo social y cultural clave para la preservación de los idiomas quechua y aimara[cite: 753].
* [cite_start]El diseño permite que la aplicación sea escalable y eficiente para futuras mejoras tecnológicas[cite: 754].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

<span id="_Toc52661356" class="anchor"></span>**RECOMENDACIONES**
* [cite_start]Validar el contenido constantemente con especialistas lingüísticos para asegurar la precisión cultural[cite: 756].
* Optimizar el uso de APIs de IA para controlar costos y mejorar el rendimiento[cite: 757].
* [cite_start]Realizar pruebas con usuarios reales para perfeccionar la experiencia de usuario final[cite: 758].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

<span id="_Toc52661357" class="anchor"></span>**BIBLIOGRAFIA**
* [cite_start]IEEE Std 830-1998, Recommended Practice for Software Requirements Specifications[cite: 661].
* Estándar Internacional ISO/IEC 27001 sobre Seguridad de la Información[cite: 534].

<div style="page-break-after: always; visibility: hidden">\pagebreak</div>

<span id="_Toc52661358" class="anchor"></span>**WEBGRAFIA**
* [cite_start]Firebase Documentation: https://firebase.google.com/docs[cite: 663].
* [cite_start]Android Developers - Jetpack Compose: https://developer.android.com/jetpack/compose[cite: 723].
* Ley N° 29733 - Protección de Datos Personales (Perú)[cite: 514].
* [cite_start]Ley N° 29571 - Código de Protección y Defensa del Consumidor (Perú)[cite: 524].