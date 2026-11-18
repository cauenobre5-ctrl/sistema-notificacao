# Gera docs/Documentacao-Sistema-Notificacao.pdf a partir de docs/documentacao.html
# (requer Google Chrome ou Microsoft Edge e acesso à internet para carregar Mermaid/highlight.js)
$ErrorActionPreference = 'Stop'
$dir = Split-Path -Parent $MyInvocation.MyCommand.Path
$html = Join-Path $dir 'documentacao.html'
$pdf = Join-Path $dir 'Documentacao-Sistema-Notificacao.pdf'

$navegadores = @(
    "$env:ProgramFiles\Google\Chrome\Application\chrome.exe",
    "${env:ProgramFiles(x86)}\Microsoft\Edge\Application\msedge.exe"
)
$exe = $navegadores | Where-Object { Test-Path $_ } | Select-Object -First 1
if (-not $exe) { throw 'Chrome ou Edge não encontrado.' }

$url = 'file:///' + ($html -replace '\\', '/')
& $exe --headless=new --disable-gpu --no-pdf-header-footer --virtual-time-budget=20000 `
    "--print-to-pdf=$pdf" $url | Out-Null
Start-Sleep -Seconds 1
Write-Host "PDF gerado em: $pdf"
