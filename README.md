# Introdução
Repositório para os projetos semanais e para o projeto final da disciplina MC322-1s2025.

Acesso ao código em semanais/src/lab*, assim como o readme relatando as mudanças entre os laboratórios.

Alunos: Arthur Costa Barreto (206325), Vitor Benaglia Albertini (206645).

Turma: A

# Estrutura do projeto
```
MC322
├── MC322.iml
├── README.md
└── semanais
    ├── semanais.iml
    └── src
        ├── lab01
        │   ├── Ambiente.java
        │   ├── Main.java
        │   └── Robo.java
        ├── lab02
        │   ├── Ambiente.java
        │   ├── Main.java
        │   ├── README.md
        │   └── robos
        │       ├── Robo.java
        │       ├── RoboAereo.java
        │       ├── RoboTerrestre.java
        │       ├── robos_aereos
        │       │   ├── RoboBombardeiro.java
        │       │   └── RoboExplosivo.java
        │       └── robos_terrestres
        │           ├── RoboCombustivel.java
        │           └── RoboSolar.java
        ├── lab03
        │   ├── Ambiente.java
        │   ├── Main.java
        │   ├── README.md
        │   ├── diagrama_lab03.drawio
        │   ├── obstaculos
        │   │   ├── Obstaculo.java
        │   │   └── TipoObstaculo.java
        │   ├── robos
        │   │   ├── Robo.java
        │   │   ├── RoboAereo.java
        │   │   ├── RoboTerrestre.java
        │   │   ├── robos_aereos
        │   │   │   ├── RoboBombardeiro.java
        │   │   │   └── RoboExplosivo.java
        │   │   └── robos_terrestres
        │   │       ├── RoboCombustivel.java
        │   │       └── RoboSolar.java
        │   └── sensores
        │       ├── Sensor.java
        │       ├── SensorAltitude.java
        │       └── SensorClasse.java
        ├── lab04
        │   ├── Ambiente.java
        │   ├── Main.java
        │   ├── README.md
        │   ├── comunicacao
        │   │   └── CentralComunicacao.java
        │   ├── diagrama_lab04.drawio
        │   ├── entidade
        │   │   ├── Entidade.java
        │   │   ├── TipoEntidade.java
        │   │   └── robos
        │   │       ├── Autonomo.java
        │   │       ├── Comunicavel.java
        │   │       ├── Estado.java
        │   │       ├── Robo.java
        │   │       ├── RoboAereo.java
        │   │       ├── RoboTerrestre.java
        │   │       ├── Sensoreavel.java
        │   │       ├── robos_aereos
        │   │       │   ├── Explosivos.java
        │   │       │   ├── RoboBombardeiro.java
        │   │       │   └── RoboExplosivo.java
        │   │       └── robos_terrestres
        │   │           ├── Energizavel.java
        │   │           ├── RoboCombustivel.java
        │   │           └── RoboSolar.java
        │   ├── excecoes
        │   │   ├── ColisaoException.java
        │   │   ├── ErroComunicacaoException.java
        │   │   ├── ForaDosLimitesException.java
        │   │   ├── RoboDesligadoException.java
        │   │   ├── SemAmbienteException.java
        │   │   └── SemCombustivelException.java
        │   ├── obstaculos
        │   │   ├── Obstaculo.java
        │   │   └── TipoObstaculo.java
        │   └── sensores
        │       ├── Sensor.java
        │       ├── SensorAltitude.java
        │       └── SensorClasse.java
        └── semanais.iml
```

# Como compilar e executar nosso código
## 1. Vá para o diretório raiz do projeto
Para isso, no terminal faça:
```bash
cd ~/MC322
```
## 2. Encontre o laboratório de número X e compile o código
```bash
find semanais/src/lab0X -name "*.java" > sources.txt
javac -d out @sources.txt
```
## 3. Execute-o
```bash
java -cp out lab0X.Main
```
