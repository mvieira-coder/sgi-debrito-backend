# SGI-Debrito - backend

Projeto academico de cadastros de uma autoescola, com Spring Boot, Java 17, Maven, JPA e MySQL.

## Escopo

CRUDs de Funcionarios, Clientes e Usuarios. Arquitetura: entity, repository, service e controller. A pasta dto contém os três objetos simples usados para receber os dados de cadastro e atualização. Cada DTO copia somente os campos permitidos para a entidade, sem receber o ID.
O DELETE remove o registro de verdade. O login e simulado no frontend; este backend nao implementa autenticacao nem autorizacao. O campo papel e apenas armazenado.

| Modulo | Rota |
|---|---|
| Funcionarios | /funcionarios |
| Clientes | /clientes |
| Usuarios | /usuarios |

Cada rota oferece GET (listar), GET /{id}, POST, PUT /{id} e DELETE /{id}.
POST retorna 201, consultas e atualizacoes retornam 200, exclusao retorna 204.
ID inexistente retorna 404. O CORS permite http://localhost:4200.

## Executar com MySQL

1. Instale JDK 17 e MySQL, e configure JAVA_HOME.
2. Crie o banco com `CREATE DATABASE sgi_debrito;`.
3. A configuracao padrao usa localhost:3306 e usuario root. Configure DB_PASSWORD com a senha do seu MySQL nas variaveis de ambiente do terminal ou da configuracao Run do IntelliJ. Nao coloque a senha em um commit.
4. Na pasta do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API inicia em http://localhost:8080. As tabelas sao criadas/atualizadas pelo JPA.

## Testes e cobertura

```powershell
.\mvnw.cmd clean verify
```

Sao 30 testes unitarios (10 por service), 18 cenarios de integracao e 1 teste de inicializacao.
Os unitarios usam JUnit 5 e Mockito, com @Mock, @InjectMocks, when/thenReturn e verify.
Os testes de integracao usam MockMvc, contexto Spring real e H2 em memoria, separado do MySQL.
Os cenarios cobrem CRUD completo, persistencia, exclusao fisica, IDs inexistentes, JSON malformado, ID nao numerico, CORS e tentativa de trocar o ID pelo corpo da requisicao.

O projeto preserva JUnit 5, exigido na atividade. Como o Spring Boot 4.1.1 usa Spring Test com integracao voltada ao JUnit 6, o contexto de integracao e iniciado explicitamente por SpringApplicationBuilder. A logica dos CRUDs continua a mesma.

O JaCoCo gera `target/site/jacoco/index.html`. `verify` verifica o minimo de 90% configurado; `package` tambem gera o relatorio, como na aula. A copia entregue esta em `relatorios/jacoco/index.html`.
Se o Windows bloquear a limpeza de target, feche os processos que estejam usando essa pasta e tente novamente.

## Teste de carga

Abra `testes-carga/README.md` para o cenario, resultados e comandos de repeticao.
O plano JMeter esta em `testes-carga/consultas.jmx`; o dashboard em `testes-carga/dashboard/index.html`.
Foram 300 consultas locais, com 10 usuarios simulados e zero erros. O teste usa H2 e poucos dados; nao representa capacidade de producao.

## SonarQube

As evidencias da primeira e da segunda analises ficam em `relatorios/sonarqube/`.
Consulte o README dessa pasta para a comparacao e as limitacoes restantes.
Para repetir, inicie um SonarQube local, crie o projeto `sgidebrito`, gere um token e configure SONAR_TOKEN no ambiente do seu terminal. Nao salve o token no repositorio.
Depois de `verify`, execute:

```powershell
.\mvnw.cmd org.sonarsource.scanner.maven:sonar-maven-plugin:5.8.0.7211:sonar "-Dsonar.host.url=http://127.0.0.1:9000" "-Dsonar.projectKey=sgidebrito" "-Dsonar.projectName=SGI-Debrito" "-Dsonar.sources=src/main/java" "-Dsonar.tests=src/test/java" "-Dsonar.scm.disabled=true"
```

O servidor usado foi SonarQube Community Build 26.10.0.133078, executado com Java 25. O backend e os testes foram compilados e executados com Java 17. O scanner pode obter seu proprio runtime; o projeto continua com java.version=17.

## Entrega

- Codigo, testes e relatorios estao versionados no repositorio.
- `target/` e `.idea/` nao entram na entrega.
- O ZIP deve ser exportado do mesmo commit enviado ao GitHub, usando `git archive`.
- Repositorio: https://github.com/mvieira-coder/sgi-debrito-backend
- Branch desta atividade: test/avaliacao-mensal.

## Fontes das ferramentas

- JMeter: https://jmeter.apache.org/download_jmeter.cgi
- SonarQube: https://www.sonarsource.com/products/sonarqube/downloads/
- JaCoCo: https://www.jacoco.org/jacoco/
