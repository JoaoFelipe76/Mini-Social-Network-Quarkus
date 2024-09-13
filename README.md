# Mini Social Network API

## Sobre
Uma API RESTful construída em **Quarkus** para uma mini rede social. Esta API permite que os usuários criem contas, sigam e deixem de seguir outros usuários, publiquem, editem e excluam conteúdo, além de visualizar postagens. As postagens são visíveis apenas para os seguidores, e os usuários só podem ver as postagens das pessoas que seguem.

## Funcionalidades
- **Criação de Contas**: Registre novos usuários na plataforma.
- **Seguir/Deixar de Seguir**: Siga ou pare de seguir outros usuários para controlar o conteúdo que você vê.
- **Postagem de Conteúdo**: Publique textos e compartilhe com seus seguidores.
- **Edição e Exclusão de Postagens**: Edite ou exclua suas postagens quando necessário.
- **Visualização de Postagens**: Visualize as postagens dos usuários que você segue.
  
## Regras de Visibilidade
- **Postagens**: Apenas os seguidores podem ver suas postagens, garantindo privacidade.
- **Timeline Personalizada**: Você só pode ver postagens das pessoas que segue.

## Tecnologias Utilizadas
- **Quarkus**: Framework de back-end para desenvolvimento de APIs leves e de alta performance.
- **JAX-RS**: Implementação de APIs RESTful.
- **Hibernate ORM**: Gerenciamento de banco de dados relacional.
- **PostgreSQL**: Banco de dados utilizado para armazenar as informações dos usuários, posts e seguidores.

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/JoaoFelipe76/mini-social-network-Quarkus.git

