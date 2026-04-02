Refatoração de Sistema de Olimpíadas de Xadrez (SOLID)

Este projeto foi uma atividade de refatoração onde peguei um código que estava todo dentro da classe App e organizei seguindo os princípios do SOLID. 
O objetivo foi deixar o código mais limpo e fácil de mexer, sem usar frameworks externos (como Spring), apenas Java puro.

A principal mudança foi separar as responsabilidades. Antes, o App.java fazia tudo: guardava os dados, desenhava o tabuleiro e calculava as notas. Agora, cada coisa tem seu lugar: 
Pastas (Pacotes), criei o pacote .repository para cuidar dos dados, Interfaces criei "contratos" para o visualizador 
do tabuleiro e para a calculadora de notas, criei classes Específicas só para o tabuleiro e só para o cálculo.

Sobre os princípios SOLID aplicados: 
No S (Responsabilidade Única), cada classe agora faz apenas uma função, como os Repositories que só guardam os dados e a Calculadora que só processa os pontos. 
No O (Aberto/Fechado), o código permite extensões, como trocar o visualizador do console por uma interface gráfica, sem precisar mexer no App. 
No L (Substituição de Liskov), o uso de interfaces garante que qualquer nova implementação encaixe perfeitamente no sistema sem causar erros. 
No I (Segregação de Interface), as interfaces são curtas e específicas para cada necessidade, evitando métodos desnecessários. 
E no D (Inversão de Dependência), o App passou a depender de interfaces em vez de classes fixas, o que facilita muito a manutenção e futuras trocas de componentes.
