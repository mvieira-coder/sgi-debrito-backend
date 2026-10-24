# Relatório 2 - SonarQube

Projeto: SGI-Debrito. Análise final realizada em 24/10/2026, reexecutada após a inclusão dos DTOs.

| Resultado | Primeira análise | Após as correções |
|---|---:|---:|
| Bugs | 0 | 0 |
| Problemas de manutenção (code smells) | 6 | 0 |
| Vulnerabilidades apontadas | 6 | 0 |
| Duplicação de código | 0% | 0% |
| Cobertura de linhas | 97,1% | 98,1% |

## Correções realizadas

**Dependências pelo construtor:** nos três controllers e nos três services, o @Autowired passou dos campos para os construtores. Isso resolveu os 6 problemas de manutenção.

**DTOs nos endpoints:** foram criados ClienteDto, FuncionarioDto e UsuarioDto para receber os dados de cadastro e atualização. Eles contêm somente os campos desses cadastros, sem o ID, e convertem os dados para as entidades. Os métodos POST e PUT deixaram de receber entidades diretamente, resolvendo os 6 alertas de vulnerabilidade.

## Resultado

Os 49 testes passaram sem erros. Foram acrescentados testes para confirmar que um ID enviado no corpo da requisição não muda a identidade do registro. Os campos de cadastro e atualização também foram conferidos.

O Quality Gate ficou aprovado. Não restaram apontamentos abertos nesta análise do SonarQube.

Os arquivos JSON desta pasta contêm os resultados exportados da análise final.
