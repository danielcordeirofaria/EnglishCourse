CREATE TABLE tbl_users (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome_usuario VARCHAR(255) NOT NULL,
    email VARCHAR(40) NOT NULL,
    login VARCHAR(40) NOT NULL UNIQUE,
    password TEXT NOT NULL, -- Adicionei o tipo de dado (assumindo que seja uma string hash)
    data_nascimento DATE NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    whatsapp VARCHAR(13) NOT NULL,
    data_compra DATE NOT NULL,
    escola_da_prova VARCHAR(40) NOT NULL CHECK (escola_da_prova IN ('CMC', 'CMRJ', 'CMSP', 'CMBel', 'CMBH', 'CMB', 'CMCG', 'CMF', 'CMJF', 'CMM', 'CMPA', 'CMR', 'CMS', 'CMSM')),
    roles VARCHAR(10) NOT NULL CHECK (roles IN ('ADMIN', 'USER'))
);
