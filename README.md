# FluxCred Payment API

A FluxCred Payment API é responsável por gerenciar as parcelas de empréstimos dentro do sistema FluxCred. Esta API permite a criação, listagem e pagamento de parcelas de empréstimos, garantindo um fluxo eficiente e seguro para os clientes da plataforma.

## Visão Geral

A FluxCred Payment API utiliza uma arquitetura hexagonal para promover a separação de preocupações e facilitar a manutenção e evolução do código. Essa arquitetura permite que as diferentes partes da aplicação sejam facilmente substituídas ou modificadas sem afetar outras partes do sistema.

## Spring Security

Para garantir a segurança dos dados dos clientes, a FluxCred Payment API utiliza o Spring Security para autenticação e autorização. Esse framework oferece recursos poderosos para proteger endpoints, controlar o acesso de usuários e garantir a integridade dos dados.

## RabbitMQ

A FluxCred Payment API utiliza o RabbitMQ como sistema de mensageria para comunicação assíncrona entre os diferentes componentes do sistema. Esse sistema de filas de mensagens permite uma integração eficiente e escalável entre os serviços, garantindo a entrega confiável de mensagens mesmo em ambientes distribuídos.

## Design Patterns

Na construção da FluxCred Payment API, foram aplicados diversos padrões de projeto para promover a reutilização de código, simplificar a manutenção e melhorar a escalabilidade. Alguns dos padrões utilizados incluem:

- **Factory Method:** Utilizado na criação de objetos complexos, como as parcelas de empréstimos.
- **Strategy:** Implementado em casos como o cálculo do valor das parcelas, onde diferentes estratégias podem ser aplicadas com base nas características do empréstimo.
- **DTO (Data Transfer Object):** Utilizado para transferir dados entre as camadas da aplicação, garantindo a separação entre a lógica de negócios e a camada de apresentação.

## PostgreSQL

O PostgreSQL é o banco de dados escolhido para armazenar os dados da aplicação.
Isso não estava explicito no arquivo de documentação do projeto, mas pelo desenho da modelagem dos campos entende-se que deve ser usado um banco relacional.
Escolhi ele por que oferece recursos avançados de segurança, desempenho e escalabilidade, atendendo às necessidades do FluxCred Core API.

## Testes Unitários

A FluxCred Payment API possui uma cobertura abrangente de testes unitários para garantir a qualidade e estabilidade do código. Os testes unitários são escritos usando o framework de teste JUnit e Mockito para simular o comportamento de classes e componentes externos.

Devido a restrições de tempo, os testes integrados e o uso de tecnologias como testContainers não puderam ser implementados neste momento. No entanto, os testes unitários fornecem uma camada sólida de garantia de qualidade para as funcionalidades da API, permitindo a identificação precoce de regressões e problemas de código.

## Rodando a Aplicação com Docker e Docker-Compose

Para executar a aplicação localmente, siga estas etapas:

1. Certifique-se de ter o Docker e o Docker Compose instalados em seu sistema.
2. Antes de mais nada certifique-se que todos os containers criados na fluxcre-core esteja de pé.
3. Clone o repositório do FluxCred Payment API.
4. Navegue até o diretório raiz do projeto.
5. Navegue até o diretório docker do projeto.
6. Execute o comando `docker-compose -p fluxcred up --build -d` para construir e iniciar os contêineres da aplicação.
7. Após a inicialização, a API estará disponível em `http://localhost:8081`.

## Conclusão

A FluxCred é uma solução simples mas completa, construída com tecnologias modernas e boas práticas de desenvolvimento.
Busquei desenvolver algo que me destaque dos demais candidatos e ao mesmo tempo mostre uma parte do meu conhecimento técnico e atenda o curto prazo para o teste de 3 dias.

