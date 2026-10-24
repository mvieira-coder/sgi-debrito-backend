# Teste de carga - JMeter

Execucao local em 23/10/2026, com Apache JMeter 5.6.3 e Java 17.

## Cenario

- 10 usuarios (threads), iniciados ao longo de 5 segundos.
- 10 repeticoes por usuario, com pausa de 200 ms antes de cada consulta.
- GET /funcionarios, GET /clientes e GET /usuarios: 100 consultas por endpoint.
- Aplicacao isolada em http://127.0.0.1:8081, banco H2 em memoria, um registro ficticio por modulo.
- Cada requisicao verifica se o codigo HTTP e 200.

## Resultados

| Consulta | Requisicoes | Erros | Media (ms) | P95 (ms) | Maximo (ms) |
|---|---:|---:|---:|---:|---:|
| GET /funcionarios | 100 | 0 | 28.71 | 195.75 | 381 |
| GET /clientes | 100 | 0 | 9.86 | 27.95 | 38 |
| GET /usuarios | 100 | 0 | 12.17 | 33.85 | 77 |
| Total | 300 | 0 | 16.91 | 36.00 | 381 |

## Analise

As 300 consultas retornaram HTTP 200. P95 significa que 95% das respostas ficaram nesse tempo ou abaixo. Nao foi observado erro sob esta carga. O pico de 381 ms ocorreu no inicio da execucao; aquecimento e concorrencia com processos locais podem influenciar, mas este teste nao identifica a causa. Nao foram medidos CPU, memoria ou consultas lentas, portanto nao e possivel afirmar qual seria o gargalo.

Este e um teste pequeno de leitura, com apenas um registro em cada tabela e H2. Nao mede escrita concorrente, grandes volumes, MySQL, rede externa ou capacidade maxima. O SonarQube estava iniciando no mesmo computador durante a execucao.

## Arquivos

- consultas.jmx: plano que pode ser aberto no JMeter.
- resultados.jtl: resultados brutos de cada requisicao.
- dashboard/index.html: relatorio HTML gerado pelo JMeter.

## Repetir

1. Inicie o backend de teste em um terminal na raiz do projeto:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.useTestClasspath=true" "-Dspring-boot.run.profiles=test" "-Dspring-boot.run.arguments=--server.port=8081 --server.address=127.0.0.1 --spring.config.additional-location=file:./src/test/resources/application-test.properties"
```

2. Confira no log a URL jdbc:h2:mem:sgi_test. Em outro terminal, execute o script de dados ficticios:

```powershell
.\testes-carga\preparar-dados.ps1
```

3. Com JMeter instalado, rode (use novos nomes para nao sobrescrever a evidencia entregue):

```powershell
jmeter.bat -n -t testes-carga/consultas.jmx -l resultados-novos.jtl -e -o dashboard-novo
```

4. Abra dashboard-novo/index.html. Encerre o backend de teste com Ctrl+C; os dados em memoria sao descartados.
