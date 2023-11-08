# Spring Email Service With Clean Architecture 📩

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Esse projeto foi feito para ser um back-end de uma API RESTful de um serviço de envios de e-mails por meio do protocolo **SMTP** utilizando o **Spring Boot**.
Ele recebe o envio do e-mail através da API e envia para um serviço externo de envio de gerenciamento de e-mails SMTP, para que então seja enviado e-mail para o destinário final.

## 💻 Arquitetura da aplicação

A aplicação foi desenvolvida seguindo os princípios do Clean Architecture

- `/controllers/` - Camada de Controllers da aplicação RESTful
  - Classe `EmailSenderController` - É o controller RESTful da aplicação Spring
- `/infrastrucutre` - É o provedor do serviço de e-mail externo, implementação do serviço da biblioteca [Simple Java Mail](https://www.simplejavamail.org/) para o envio de e-mails
    - Classe `SimpleJavaMailConfig` - Guarda o objeto `Mailer` **Bean** de configuração do Simple Java Mail, instanciado automaticamente pelo Spring
    - Classe `SimpleJavaMailSender` - Disponibiliza um **Service** com o propósito de ser um Gateway usado na aplicação, sendo a **regra de negócio** para o envio de e-mails
- `/core/` - Diretório que guarda os **Casos de Usos** e regras de negócio da aplicação
  - Interface `EmailSenderUseCase` - é uma interface (contrato) de regra de negócio do serviço de envio e-mail (alto nível da aplicação), sendo também agnóstica (não depende) em relação aos outros componentes da aplicação
  - Record `EmailRequest` - É o DTO utilizado no _Body_ da requisição do Controller principal
- `/application/` - É a camada intermediaria que dialoga com os casos de usos e os serviços externos (que estão no pacote infrastructure)
  - Arquivo `EmailSenderService` - é o serviço em si que é utilizado no controller
- `/adapters/` - Disponibiliza interfaces que **adaptam** o mundo exterior (APIs externas como a classe [SimpleJavaMailSender](src/main/java/com/brendongomes/emailservice/infrastructure/simplejavamail/SimpleJavaMailSender.java) para a aplicação, com o 
  - Interface `EmailSenderGateway` - Define a interface (contrato) que todas as APIs externas de envio de e-mails devem implementar

## 🔥 Testando aplicação

O endpoint da aplicação está em `http://localhost:8080/api/email` e recebe o método POST contendo o seguinte **_body_** em arquivo **json**:

```json
{
	"to": "Email para quem será enviado <johndoe@email.com>",
	"subject": "Nome do assunto",
	"body": "<p>Mensagem (body) da aplicação que pode ser um arquivo HTML</p>"
}
```

Para testar é necessário criar uma conta no <https://mailtrap.io> e com as configurações do mailtrap, definir as seguintes configurações no arquivo `application.properties` localizado em `/src/main/resources`

```properties
mailtrap.username=usernameDefinidoNoMailtrap
mailtrap.password=passwordDefinidoNoMailtrap
```

---

> Observação: essa aplicação foi desenvolvida seguindo o [Vídeo da Fernanda Kipper](https://www.youtube.com/watch?v=eFgeO9M9lLw&t=462s&ab_channel=FernandaKipper%7CDev) mas com a utilização do serviço de e-mail Mailtrap.io, com a utilização da biblioteca Simple Java Mail

<h3 align="center">
    Feito com ☕ por <a href="https://github.com/Brendon3578"> Brendon Gomes</a>
</h3>
