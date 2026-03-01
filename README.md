# RabbitMQ Notification Service

Um serviço de notificação Spring Boot que utiliza RabbitMQ para processamento assíncrono de mensagens.

## 📋 Descrição

Este projeto implementa um sistema de envio de notificações usando RabbitMQ como message broker. A aplicação expõe uma API REST para receber requisições de notificação e as processa de forma assíncrona através de filas RabbitMQ.

## 🏗️ Arquitetura

- **Producer**: [NotificationController](cci:2://file:///C:/Projetos/Repositorio/rabbitmq/src/main/java/com/legsoft/rabbitmq/controller/NotificationController.java:10:0-23:1) recebe requisições HTTP e envia mensagens para a fila RabbitMQ
- **Consumer**: [NotificationConsumer](cci:2://file:///C:/Projetos/Repositorio/rabbitmq/src/main/java/com/legsoft/rabbitmq/consumer/NotificationConsumer.java:7:0-23:1) processa as mensagens da fila de forma assíncrona
- **Message**: [NotificationMessage](cci:2://file:///C:/Projetos/Repositorio/rabbitmq/src/main/java/com/legsoft/rabbitmq/domain/NotificationMessage.java:4:0-10:1) é o objeto que representa a notificação
- **Config**: [RabbitConfig](cci:2://file:///C:/Projetos/Repositorio/rabbitmq/src/main/java/com/legsoft/rabbitmq/config/RabbitConfig.java:1:0-22:49) configura as filas, exchanges e bindings do RabbitMQ

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 4.0.3**
- **Spring AMQP (RabbitMQ)**
- **Spring WebMVC**
- **Maven**
- **RabbitMQ**

## 📦 Dependências Principais

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
```

## 🔧 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- RabbitMQ Server (localhost:5672)

## 🛠️ Configuração

### RabbitMQ

Configure as credenciais do RabbitMQ em [src/main/resources/application.yml](cci:7://file:///C:/Projetos/Repositorio/rabbitmq/src/main/resources/application.yml:0:0-0:0):

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### Instalação do RabbitMQ

#### Docker (Recomendado)
```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

#### Manual
- Download e instalação do RabbitMQ: https://www.rabbitmq.com/download.html

## 🏃‍♂️ Executando a Aplicação

1. Clone o repositório:
```bash
git clone <repository-url>
cd rabbitmq
```

2. Compile e execute:
```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## 📡 API Endpoints

### Enviar Notificação

**POST** `/api/notifications/send`

**Request Body:**
```json
{
    "to": "john@example.com",
    "subject": "Welcome",
    "body": "Hello John!"
}
```

**Response:**
```json
"Notification queued successfully!"
```

**Exemplo com cURL:**
```bash
curl -X POST http://localhost:8080/api/notifications/send \
  -H "Content-Type: application/json" \
  -d '{
    "to": "john@example.com",
    "subject": "Welcome",
    "body": "Hello John!"
  }'
```

## 🔄 Fluxo da Mensagem

1. **Request**: Cliente envia JSON para `/api/notifications/send`
2. **Producer**: [NotificationController](cci:2://file:///C:/Projetos/Repositorio/rabbitmq/src/main/java/com/legsoft/rabbitmq/controller/NotificationController.java:10:0-23:1) recebe e envia para RabbitMQ
3. **Queue**: Mensagem fica na fila `notification.queue`
4. **Consumer**: [NotificationConsumer](cci:2://file:///C:/Projetos/Repositorio/rabbitmq/src/main/java/com/legsoft/rabbitmq/consumer/NotificationConsumer.java:7:0-23:1) processa a mensagem assincronamente
5. **Processing**: Simulação de processamento (1 segundo)
6. **Log**: Mensagem é exibida no console

## 🐛 Configuração do Message Converter

O projeto utiliza `SimpleMessageConverter` com configuração de segurança para permitir desserialização das classes do domínio:

```java
@Bean
public MessageConverter messageConverter() {
    SimpleMessageConverter converter = new SimpleMessageConverter();
    converter.setAllowedListPatterns(List.of("com.legsoft.rabbitmq.domain.*"));
    return converter;
}
```

## 📊 Estrutura do Projeto

```
src/main/java/com/legsoft/rabbitmq/
├── config/
│   └── RabbitConfig.java          # Configuração RabbitMQ
├── controller/
│   └── NotificationController.java # API REST endpoints
├── domain/
│   └── NotificationMessage.java  # Modelo de dados
├── producer/
│   └── NotificationProducer.java  # Envio de mensagens
└── consumer/
    └── NotificationConsumer.java  # Processamento de mensagens
```

## 🔍 Logs

A aplicação exibe logs no console para:
- Envio de mensagens: `"Sending: NotificationMessage[...]"`
- Recebimento de mensagens: `"Received: NotificationMessage[...]"`
- Processamento: `"Notification sent to: [email]"`

## 🧪 Testes

Execute os testes com:
```bash
mvn test
```

## 📈 Monitoramento

- **RabbitMQ Management Interface**: http://localhost:15672 (guest/guest)
- **Application Logs**: Console output

## 🔐 Segurança

- Configuração de allowed list patterns para desserialização segura
- Credenciais RabbitMQ configuráveis via application.yml

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob licença MIT.

## 🆘 Troubleshooting

### Problemas Comuns

1. **Connection refused**: Verifique se o RabbitMQ está rodando
2. **Authentication failed**: Verifique credenciais no application.yml
3. **Deserialization error**: Verifique configuração do message converter

### Comandos Úteis

```bash
# Verificar status do RabbitMQ (Docker)
docker ps | grep rabbitmq

# Verificar logs da aplicação
mvn spring-boot:run | grep -E "(Sending|Received|Notification)"
```

---

**Versão**: 0.0.1-SNAPSHOT  
**Autor**: LegSoft  
**Data**: 2026
