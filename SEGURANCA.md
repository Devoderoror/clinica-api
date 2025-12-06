# Relatório de Vulnerabilidade de Segurança: Clinica API

**Sistema Alvo:** Clinica API (API RESTful Spring Boot)
**Versão:** clinica-api-final-2
**Auto-Auditoria**
**Data:** 6 de Dezembro de 2025

## 1. Resumo Executivo

Uma vulnerabilidade de segurança crítica foi identificada no mecanismo de autenticação do projeto `clinica-api-final-2`. O sistema armazena senhas de usuários em formato de texto simples ou reversível, o que constitui uma grave **Falha Criptográfica**. Essa falha viola diretamente as práticas modernas de segurança para gerenciamento de credenciais e expõe todas as contas de usuário a comprometimento no caso de uma violação do banco de dados. A remediação imediata é obrigatória.

| ID da Descoberta | Vulnerabilidade | Severidade | OWASP Top 10 | Status |
| :--- | :--- | :--- | :--- | :--- |
| CRIT-001 | Armazenamento Inseguro de Senha (Texto Simples) | **Crítica** | A02: Falhas Criptográficas | Aberto |

## 2. Detalhes da Vulnerabilidade

### CRIT-001: Armazenamento Inseguro de Senha (Texto Simples)

O modelo de usuário e o serviço de autenticação da aplicação não implementam um algoritmo seguro de hash de senha de mão única (como bcrypt, Argon2 ou Scrypt). Em vez disso, o sistema se baseia em uma comparação direta de strings contra um valor armazenado rotulado como `senhaHash`, que é funcionalmente uma senha em texto simples.

*   **Severidade:** Crítica
*   **OWASP Top 10 2021:** A02: Falhas Criptográficas
*   **CWE:** CWE-257: Armazenamento de Senhas em Formato Recuperável

### 2.1. Componentes Afetados

A vulnerabilidade está enraizada no modelo de usuário principal e na lógica de autenticação:

1.  **`br.com.clinica.model.Usuario.java`**
    A classe `Usuario` armazena a senha no campo `senhaHash` e expõe um método de comparação simples.

    ```java
    // Em br.com.clinica.model.Usuario.java
    private String senhaHash; // O valor armazenado é "123" em texto simples

    public boolean autenticar(String senha) {
        // Comparação direta de string contra o valor armazenado em texto simples
        return this.senhaHash.equals(senha);
    }
    ```

2.  **`br.com.clinica.service.ClinicaService.java`**
    A camada de serviço chama o método de autenticação vulnerável.

    ```java
    // Em br.com.clinica.service.ClinicaService.java
    public Optional<Usuario> autenticarUsuario(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                // Chama o método autenticar vulnerável
                .filter(u -> u.autenticar(senha));
    }
    ```

### 2.2. Prova de Conceito (PoC)

O arquivo `DataLoader.java` inicializa usuários com a senha "123" armazenada diretamente no campo `senhaHash`, confirmando o método de armazenamento inseguro.

```java
// Em br.com.clinica.config.DataLoader.java (ou ClinicaService.inicializarUsuarios)
usuarioRepository.save(new Administrador("Adm. Supervisor", "prof@clinica.com", "123"));
usuarioRepository.save(new Cliente("Cliente Estagiário", "aluno@clinica.com", "123"));
```

## 3. Impacto

O armazenamento inseguro de senhas leva aos seguintes riscos de alto impacto:

*   **Comprometimento Total de Credenciais:** Qualquer invasor que obtenha acesso ao banco de dados da aplicação (por exemplo, através de um ataque bem-sucedido de Injeção SQL, uma configuração incorreta de backup ou um servidor comprometido) obterá imediatamente todas as senhas de usuário em texto simples.
*   **Movimentação Lateral/Tomada de Conta:** Como muitos usuários reutilizam senhas em diferentes serviços, as credenciais comprometidas podem ser usadas para assumir contas em outras plataformas (por exemplo, e-mail, banco, mídia social).
*   **Dano Reputacional:** Uma violação de dados envolvendo senhas em texto simples resulta em perda significativa de confiança do usuário e penalidades regulatórias severas sob as leis de proteção de dados.

## 4. Remediação

As seguintes etapas são obrigatórias para proteger o processo de autenticação:

### 4.1. Ação Imediata: Implementar Hash Forte

A aplicação deve ser refatorada para usar uma biblioteca moderna e padrão da indústria para hash de senha. Para uma aplicação Spring Boot, a abordagem recomendada é utilizar a interface `PasswordEncoder` do Spring Security, especificamente o `BCryptPasswordEncoder` ou uma implementação forte similar.

**Etapas:**

1.  **Adicionar Dependência do Spring Security:** Garantir que a dependência necessária do Spring Security esteja incluída no `pom.xml`.
2.  **Configurar PasswordEncoder:** Definir um bean `BCryptPasswordEncoder` em uma classe de configuração.
3.  **Atualizar Modelo de Usuário:** Remover o método `autenticar` de `Usuario.java`, pois a lógica de comparação será movida para a camada de serviço.
4.  **Atualizar Criação de Usuário:** Ao criar novos usuários (por exemplo, em `DataLoader` ou um endpoint de registro), a senha **DEVE** ser hasheada antes de ser salva no banco de dados.

    ```java
    // Exemplo: Hash da senha antes de salvar
    String rawPassword = "123";
    String hashedPassword = passwordEncoder.encode(rawPassword);
    usuarioRepository.save(new Administrador("...", "...", hashedPassword));
    ```

5.  **Atualizar Lógica de Autenticação:** Modificar `autenticarUsuario` para usar o `PasswordEncoder` para verificação.

    ```java
    // Exemplo: Lógica de autenticação segura em ClinicaService
    public Optional<Usuario> autenticarUsuario(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(senha, u.getSenhaHash()));
    }
    ```

### 4.2. Ação de Longo Prazo: Migrar Senhas Existentes

Para qualquer ambiente de produção, um plano deve ser executado para migrar as senhas existentes em texto simples para o novo formato hasheado. Isso geralmente envolve:

*   Forçar todos os usuários existentes a redefinir suas senhas no próximo login.
*   Durante o processo de redefinição de senha, a nova senha é salva usando o algoritmo de hash forte.

## 5. Conclusão

O projeto `clinica-api-final-2` fornece uma valiosa estrutura educacional para POO e RBAC. No entanto, o armazenamento de senha em texto simples representa uma falha de segurança crítica que deve ser abordada imediatamente para garantir a integridade e confidencialidade dos dados do usuário. A implementação de um mecanismo robusto de hash de senha é a melhoria de segurança mais importante para esta aplicação.
