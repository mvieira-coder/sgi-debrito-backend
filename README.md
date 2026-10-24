# SGI-Debrito

Backend de um sistema de autoescola, com cadastro, consulta, alteração e exclusão de Funcionários, Clientes e Usuários. Desenvolvido com Java 17, Spring Boot, Maven e MySQL. O login é simulado no frontend.

## Como executar

1. Instale o Java 17 e o MySQL.
2. Crie o banco: `CREATE DATABASE sgi_debrito;`.
3. A conexão usa `localhost:3306` e usuário `root`. Configure a variável de ambiente `DB_PASSWORD` com a senha do MySQL.
4. No terminal, dentro da pasta do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API fica em `http://localhost:8080`, com as rotas `/funcionarios`, `/clientes` e `/usuarios`.

## Como executar os testes

```powershell
.\mvnw.cmd clean verify
```

Os testes usam JUnit, Mockito e banco H2 em memória. Foram executados 49 testes sem erros, com 98,1% de cobertura de linhas.

## Relatórios da entrega

- JaCoCo: abra `relatorios/jacoco/index.html` no navegador. Para gerar um novo relatório, execute os testes e abra `target/site/jacoco/index.html`.
- JMeter: plano, resultados e instruções na pasta [testes-carga](testes-carga/README.md).
- SonarQube: [primeira análise](relatorios/sonarqube/primeira/README.md) e [análise após as correções](relatorios/sonarqube/segunda/README.md).
