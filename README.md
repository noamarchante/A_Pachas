Manual de usuario
En esta sección se especifica el manual de usuario y los requisitos mínimos
y recomendados a nivel de software para que cualquier persona que los cumpla
pueda ejecutar la aplicación en su propio ordenador.
11.1 Requisitos
Para proceder a la instalación debe asegurarse el cumplimiento de
los requisitos necesarios para garantizar su correcto funcionamiento.
- JDK 1.8
- Java 8
- IntelliJ IDEA 2020.3.1
- MySQL Workbench 8.0 CE
- Node 14.17.0
- npm 6.14.13
11.2 Manual de instalación
Una vez confirmado que se cumplen los requisitos mínimos se
procede con la instalación de la aplicación siguiendo los pasos
descritos a continuación:
1. Descomprimir el archivo “Apachas.zip” en la carpeta que se crea
conveniente.
2. Abrir el programa MySQL Workbench y seleccionar el menú de
creación de una nueva conexión.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/e119e0fb-6a87-44ef-8cbe-cf96f41c431d)
3. Cubrir el formulario de creación de la conexión con los siguientes
datos:
Connection Name: Apachas
Connection Method: Standard(TCP/IP)
Hostname: 127.0.0.1
Port: 3306
Username: root
Password: abc123.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/fb8c408d-4c9f-491b-ae7a-6ae11e48f00c)
El nombre de usuario y la contraseña deben coincidir con las
especificadas en el archivo “application.properties” situado en la
ruta “APachas\src\main\resources\application.properties”.
Una vez cubiertos los datos y establecida la contraseña en “store
in vault”, se debe seleccionar el botón “test connection”
y, si todo es correcto, se cierra la ventana seleccionando el botón
“ok”.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/8ece0ab6-5a7f-4726-aa60-721d4788d567)
4. Creada la conexión se debe acceder a ella:
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/fcaa01dd-77a7-4727-a987-dda9696a8ad0)
5. Una vez en la conexión de A Pachas se selecciona el botón
para la creación de una nueva base de datos.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/676ca231-52b0-4fee-9118-32e4a572fc85)
6. Cubrir el formulario de creación de la base de datos con los
siguientes datos:
Name: apachas
Charset/Collation: utf8 utf8_bin
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/f05bb44b-7cac-4fbd-b675-ecfba7aa1c30)
7. En el entorno de desarrollo abrir la carpeta del proyecto “Apachas”
desde el archivo pom.xml en el menú “File > Open…”.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/eace5313-258e-48d1-b30f-77978cad12b6)
8. Ejecutar APachasApplication para iniciar el backend de la
aplicación.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/d1dfa0a6-9e5c-4fe6-bf91-4529b5ad028f)
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/08b54e33-cc1e-4dff-a980-5ca1ffdc7845)
9. En el terminal del entorno de desarrollo situarse en la carpeta
“APachas\src\main\resources\Apachas” y ejecutar el comando
“npm start” para iniciar el frontend.
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/4171a137-d314-46f7-98f0-c4cea2e635d3)
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/0a2c0fa9-c0bb-4086-b008-27c0e0f8e197)
![image](https://github.com/noamarchante/A_Pachas/assets/60529385/dc1a1787-2480-4fab-8664-458821b3e67b)

Como el proyecto está desarrollado en Spring Boot con Maven los
procesos de configuración y despliegue de una aplicación web se
simplifican.
El fichero pom.xml contiene todas las dependencias necesarias
para que la aplicación web pueda funcionar desde el módulo de
conexión a la base de datos hasta el módulo de despliegue del
servidor de aplicaciones.
Por otra parte, el fichero application.properties contiene todas las
configuraciones concretas del proyecto para que las
dependencias puedan establecer conexión o configurar sus
parámetros.





