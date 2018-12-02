<div align="center">
<img src="https://github.com/heroslender/HeroAPI/raw/master/logo.jpg" alt="heroslender"/>
</div>

# HeroBolsa
Este é um addon para o `HeroVender` que adiciona o conhecido sistema de bolsa de valores.
Isto é, sempre que um jogador vender pelo HeroVender, irá receber um bónus correspondente ao valor da bolsa no momento da venda.

## Comandos
 - `/bolsa` - Ver o valor da bolsa de valores no momento;
 - `/bolsa set <valor>` - Alterar o valor da bolsa de valores para o valor indicado;
 - `/bolsa reload` - Recarregar a configuração do plugin, mensagens e valores. Se alterar o delay de atualização é preciso reiniciar o servidor.

## Permissões
 - `herovender.staff` - Permissão para alterar o valor da bolsa e recarregar a configuração

## Placeholders
#### PlaceholderAPI
 - `%bolsa%` - Valor da bolsa de valores no momento
#### MVdWPlaceholderAPI
> Brevemente

## Configuração
#### Opções do plugin
```yaml
settings:
  valor:
    # Valores máximos/minimos que a bolsa pode atingir
    max: 100
    min: 0
  diferenca:
    # Valore maximo que a bolsa pode subir/descer de cada vez
    max: 20
    min: -10
  # Delay entre atualizações do valor da bolsa, em segundos
  delay-atualizar: 10
```
#### Mensagens
```yaml
mensagem:
  # Mensagem do comando /bolsa
  check-valor: '&eValor atual da bolsa: &7:bolsa:%'
  # Comandos a ser executados pela consola quando o valor da bolsa subir
  comandos-atualizar-subiu:
    - "tm bc &6Valor da bolsa subiu\n&a:D"
  # Comandos a ser executados pela consola quando o valor da bolsa descer
  comandos-atualizar-desceu:
    - "tm bc &6Valor da bolsa desceu\n&c:("
  # Mensagens enviadas para todos quando o valor da bolsa subir
  anuncio-atualizar-subiu:
    - "&eBolsa atualizada para &7:bolsa:% &2\u25b2 &a:diferenca:%!"
  # Mensagens enviadas para todos quando o valor da bolsa descer
  anuncio-atualizar-desceu:
    - "&eBolsa atualizada para &7:bolsa:% &4\u25bc &c:diferenca:%!"
```