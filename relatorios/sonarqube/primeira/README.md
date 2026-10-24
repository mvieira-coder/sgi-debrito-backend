# Relatório 1 - SonarQube

Projeto: SGI-Debrito. Análise realizada em 23/10/2026.

| Resultado | Quantidade |
|---|---:|
| Bugs | 0 |
| Problemas de manutenção (code smells) | 6 |
| Vulnerabilidades apontadas | 6 |
| Duplicação de código | 0% |
| Cobertura de linhas | 97,1% |

## Problemas encontrados

**Uso de @Autowired nos campos:** foram encontrados 6 casos, um em cada controller e service de Clientes, Funcionários e Usuários. O SonarQube recomenda receber essas dependências pelo construtor, deixando claro o que cada classe precisa para funcionar.

**Entidades recebidas diretamente nos endpoints:** foram encontrados 6 casos, nos métodos POST e PUT dos três controllers. O SonarQube recomenda usar DTOs para limitar os campos recebidos nas requisições. Esse foi o motivo dos alertas classificados como vulnerabilidades.

Os arquivos JSON desta pasta contêm os resultados exportados do SonarQube.
