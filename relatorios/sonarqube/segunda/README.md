# Relatório 2 - SonarQube

Projeto: SGI-Debrito. Análise realizada em 24/10/2026, após as correções.

| Resultado | Antes | Depois |
|---|---:|---:|
| Bugs | 0 | 0 |
| Problemas de manutenção (code smells) | 6 | 0 |
| Vulnerabilidades apontadas | 6 | 6 |
| Duplicação de código | 0% | 0% |
| Cobertura de linhas | 97,1% | 97,6% |

## Correção realizada

Nos três controllers e nos três services, as dependências passaram a ser recebidas pelo construtor. O @Autowired foi mantido no construtor. Com isso, os 6 problemas de manutenção foram resolvidos.

Após a alteração, os 46 testes passaram sem erros. A cobertura ficou em 97,6%, considerando também as linhas adicionadas nos construtores.

## Pendências

Os 6 alertas sobre receber entidades diretamente nos métodos POST e PUT continuam. A sugestão de usar DTOs não foi aplicada porque o projeto foi mantido como CRUD simples, sem DTOs. Portanto, esses alertas não foram resolvidos.

O Quality Gate ficou OK para o código novo, mas os 6 alertas anteriores permanecem no resultado geral.

Os arquivos JSON desta pasta contêm os resultados exportados do SonarQube.
