# Execute somente com a instancia H2 de testes iniciada na porta 8081.
$dados = @{
    clientes = @{ nome='Cliente Carga'; cpf='00000000000'; telefone='4500000000'; email='teste@example.invalid' }
    funcionarios = @{ nome='Instrutor Carga'; cpf='00000000000'; telefone='4500000000'; email='teste@example.invalid'; cargo='Instrutor'; categoriaCnh='B' }
    usuarios = @{ login='usuario-carga'; senha='teste'; papel='ATENDENTE' }
}
foreach ($modulo in $dados.Keys) {
    Invoke-RestMethod -Method Post -Uri "http://127.0.0.1:8081/$modulo" -ContentType 'application/json' -Body ($dados[$modulo] | ConvertTo-Json) | Out-Null
}
Write-Host 'Criado um registro ficticio por modulo.'
