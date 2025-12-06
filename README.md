# Sistema de Gestão Clínica - API REST (Spring Boot)

## 📝 Descrição Textual do Sistema

O **Sistema de Gestão Clínica** é uma API RESTful desenvolvida em Spring Boot, projetada para gerenciar o fluxo de atendimento e supervisão em uma clínica de saúde. O sistema é focado em registrar pacientes, criar sessões de atendimento, registrar a evolução do paciente durante as sessões e garantir a supervisão obrigatória por um administrador. Ele serve como o *back-end* central para uma aplicação que precisa de autenticação de usuários (Clientes e Administradores) e persistência de dados de atendimento.

# Projeto de Segurança - Relatório de Vulnerabilidade de Segurança

Disponível em: https://github.com/Devoderoror/clinica-api/blob/main/SEGURANCA.md

# Resumo de Segurança e Vulnerabilidades - Sumário Executivo

Disponível em: https://github.com/Devoderoror/web-app/blob/main/RESUMO_SEGURANCA.md

## 🚀 Tecnologias Utilizadas

*   **Backend:** Java 11+ (Spring Boot 2.7.x)
*   **Banco de Dados:** H2 Database (Em memória, para facilitar a demonstração)
*   **Build Tool:** Apache Maven
*   **Frontend (Demo):** HTML, CSS (Bootstrap 5) e JavaScript puro.

## 🛠️ Instruções de Execução

### Pré-requisitos

Certifique-se de ter instalado em sua máquina:

1.  **Java Development Kit (JDK) 11** ou superior.
2.  **Apache Maven** (versão 3.x).

### 1. Compilação do Projeto

Navegue até o diretório raiz do projeto (`clinica-api`) e execute o comando:

```bash
mvn clean package
```

Este comando irá limpar, compilar e empacotar a aplicação em um arquivo `.jar` dentro do diretório `target/`.

### 2. Execução da Aplicação

Após a compilação, inicie a aplicação com o seguinte comando:

```bash
java -jar target/clinica-api-0.0.1-SNAPSHOT.jar
```

A aplicação será iniciada e estará acessível em `http://localhost:8080`.

### 3. Acesso à Interface de Demonstração

Acesse a interface de demonstração no seu navegador:

```
http://localhost:8080
```

A interface (construída com Bootstrap) permite testar o fluxo completo da API:
1.  **Login:** Use `aluno@clinica.com` (Cliente) ou `prof@clinica.com` (Administrador) com a senha `123`.
2.  **Cadastro de Paciente:** Cria um paciente com dados únicos.
3.  **Gestão de Sessão:** Criação de sessão, registro de evolução (pelo Cliente) e assinatura (pelo Administrador).

## 🗺️ Mapeamento UML-Código (Conceitos POO)

O projeto foi estruturado seguindo os princípios de Orientação a Objetos (POO) e o mapeamento do Diagrama de Classes:

| Conceito UML | Classe/Arquivo Correspondente | Explicação no Código |
| :--- | :--- | :--- |
| **Herança** | `Usuario.java`, `Administrador.java`, `Cliente.java` | `Administrador` e `Cliente` herdam de `Usuario`, reutilizando atributos como `nome`, `email` e `senhaHash`. |
| **Polimorfismo** | Método `getPapel()` em `Usuario.java` | O método `getPapel()` é abstrato em `Usuario` e implementado de forma diferente em `Administrador` e `Cliente`, retornando o papel específico de cada um. |
| **Encapsulamento** | Atributos `private` e métodos `public` | Todos os atributos das classes de modelo (`Usuario`, `Paciente`, etc.) são privados, e o acesso é controlado por *getters* e *setters* gerados pelo Lombok (`@Data`). A lógica de `autenticar` em `Usuario` encapsula a verificação da `senhaHash`. |
| **Associação** | `Sessao.java` | A classe `Sessao` possui associações `ManyToOne` com `Paciente`, `Usuario` (`clienteResponsavel`) e `Administrador` (`administradorSupervisor`). |
| **Entidade** | `Paciente.java`, `Sessao.java`, `Evolucao.java` | Classes que representam as entidades persistidas no banco de dados. |
