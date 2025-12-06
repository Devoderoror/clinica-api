# Sistema de Gestão Clínica - Resumo Educacional de Segurança

## Resumo Executivo

Auto-auditoria do projeto educacional de API Spring Boot, **Clinica API**, projetado para gerenciar o fluxo de trabalho clínico. O projeto demonstra com sucesso a implementação de **Controle de Acesso Baseado em Papéis (RBAC)** usando princípios de Programação Orientada a Objetos (POO), enquanto intencionalmente deixa uma vulnerabilidade crítica para servir como um ponto chave de aprendizado.

## Controles de Segurança Implementados

- ✅ **Design de Controle de Acesso Baseado em Papéis (RBAC):** Implementada uma hierarquia clara de usuários (`Administrador` e `Cliente`) usando herança e polimorfismo Java para segregar privilégios.
- ✅ **Lógica de Autorização:** Funções de negócios críticas, como a **Assinatura de Sessão** final (`assinarSessao`), são estritamente controladas, exigindo o papel de `Administrador` para execução bem-sucedida.
- ✅ **Autenticação Centralizada:** A lógica de autenticação é encapsulada dentro do modelo base `Usuario`, promovendo a reutilização de código e um ponto único de entrada para verificação de identidade.
- ✅ **Modelagem de Dados para Acesso:** As entidades são associadas a papéis de usuário específicos (por exemplo, `Sessao` está ligada a um `Paciente`, um `Cliente` como responsável e um `Administrador` como supervisor), formando a base para um controle de acesso granular.

## Vulnerabilidades Identificadas (Estudo Intencional)

### 1. Armazenamento Inseguro de Senha (CRÍTICA)

**Risco:** OWASP A02:2021 - Falhas Criptográficas

**Descoberta:** As senhas são armazenadas em formato de texto simples ou facilmente reversível (campo `senhaHash` em `Usuario.java`), sendo verificadas por meio de comparação direta de strings.

**Remediação:** Implementar um algoritmo forte de hash de senha de mão única (por exemplo, BCrypt ou Argon2) com um salt exclusivo para cada usuário.

### 2. Controle de Acesso Quebrado (ALTA)

**Risco:** OWASP A01:2021 - Controle de Acesso Quebrado

**Descoberta:** Os endpoints da API (por exemplo, em `PacienteController.java`) carecem de middleware explícito de verificação de papel, dependendo do cliente para passar o ID de usuário correto. Isso permite que um usuário potencialmente execute ações destinadas a outro papel se puder adivinhar ou manipular o ID de usuário.

**Remediação:** Implementar anotações de pré-autorização do Spring Security (`@PreAuthorize`) em todos os métodos do controlador para verificar o papel do usuário autenticado antes de processar a requisição.

### 3. Falta de Validação de Entrada (MÉDIA)

**Risco:** OWASP A03:2021 - Injeção | CWE-20: Validação de Entrada Inadequada

**Descoberta:** A entrada do usuário para campos como `nome`, `cpf` e `endereco` (em `PacienteRequest.java`) não é explicitamente sanitizada ou validada quanto a formato, tamanho ou conteúdo malicioso antes de ser persistida no banco de dados. Embora o Spring Data JPA utilize consultas parametrizadas por padrão (mitigando SQL Injection), a ausência de validação permite a entrada de dados malformados ou excessivamente longos que podem causar problemas de integridade de dados ou exploração em outras camadas da aplicação.

**Remediação:** Implementar validação de entrada robusta usando anotações de Validação de Bean Java (Jakarta Bean Validation) como `@NotBlank`, `@Size`, `@Pattern` com regex personalizado para CPF, e validadores customizados para regras de negócio específicas. Implementar sanitização de entrada para prevenir XSS em campos que possam ser exibidos em interfaces web.

## Resultados de Aprendizagem

- **Autenticação vs. Autorização:** Distinção prática entre verificar a identidade de um usuário (Autenticação) e verificar suas permissões para realizar uma ação (Autorização/RBAC).
- **POO para Segurança:** Uso de princípios centrais da Programação Orientada a Objetos (Herança, Polimorfismo, Encapsulamento) para construir uma estrutura de segurança.
- **Aplicação do OWASP Top 10:** Experiência prática na identificação e documentação de vulnerabilidades críticas como **Falhas Criptográficas** e **Controle de Acesso Quebrado**.
- **Documentação de Segurança:** Prática na criação de relatórios de segurança de nível profissional e planos de remediação.

---

## Referências

[1] OWASP Foundation. (2021). *OWASP Top 10 - 2021*. Disponível em: https://owasp.org/www-project-top-ten/

[2] MITRE Corporation. (2023). *Common Weakness Enumeration (CWE)*. Disponível em: https://cwe.mitre.org/

[3] Spring Framework. (2024). *Spring Security Reference Documentation*. Disponível em: https://docs.spring.io/spring-security/reference/index.html

[4] Jakarta EE. (2023). *Jakarta Bean Validation Specification*. Disponível em: https://jakarta.ee/specifications/bean-validation/
