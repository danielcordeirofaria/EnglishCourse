CREATE TABLE tbl_endereco
(
    id_endereco  INT PRIMARY KEY,
    logradouro         VARCHAR(255),
    numero      VARCHAR(255),
    complemento VARCHAR(255),
    bairro      VARCHAR(255),
    cidade      VARCHAR(255),
    estado      VARCHAR(255),
    cep         VARCHAR(255)
);

CREATE TABLE tbl_profs
(
    id_professor   INT PRIMARY KEY AUTO_INCREMENT,
    nome_professor VARCHAR(255) NOT NULL,
    cpf varchar(14) not null UNIQUE,
    id_endereco CHAR(36) NOT NULL,
    email     VARCHAR(40)  NOT NULL,
    login     VARCHAR(40)  NOT NULL UNIQUE,
    password  VARCHAR(20)  NOT NULL,
    whatsapp  VARCHAR(13)  NOT NULL,
    roles     VARCHAR(10)  NOT NULL CHECK (roles IN ('ADMIN', 'PROF'))

);

CREATE TABLE tbl_turmas (
                            id_turma INT AUTO_INCREMENT PRIMARY KEY,
                            nomeTurma VARCHAR(100) NOT NULL,
                            id_professor INT,
                            FOREIGN KEY (id_professor) REFERENCES tbl_profs(id_professor)
);

CREATE TABLE tbl_horarios (
                              id_horario INT AUTO_INCREMENT PRIMARY KEY,
                              id_turma INT NULL,  -- Permitindo NULL para usar ON DELETE SET NULL
                              dia_semana ENUM('SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO', 'DOMINGO') NOT NULL,
                              horario_inicio TIME NOT NULL,
                              horario_fim TIME NOT NULL,
                              FOREIGN KEY (id_turma) REFERENCES tbl_turmas(id_turma) ON DELETE SET NULL
);

CREATE TABLE tbl_alunos
(
    id_aluno_matricula INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    id_endereco CHAR(36) NOT NULL,
    data_de_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    formacao VARCHAR(255) NOT NULL,
    profissao VARCHAR(255) NOT NULL,
    modulo_feito ENUM('nenhum', 'beginners_1', 'beginners_2', 'elementary_1', 'elementary_2', 'pre_intermediate_1', 'pre_intermediate_2', 'pre_intermediate_plus_1', 'pre_intermediate_plus_2', 'intermediate_1', 'intermediate_2', 'pre_advanced_1', 'pre_advanced_2', 'advanced_1', 'advanced_2', 'advanced_plus_1', 'advanced_plus_2') NOT NULL,
    nivel ENUM('nenhum', 'beginners_1', 'beginners_2', 'elementary_1', 'elementary_2', 'pre_intermediate_1', 'pre_intermediate_2', 'pre_intermediate_plus_1', 'pre_intermediate_plus_2', 'intermediate_1', 'intermediate_2', 'pre_advanced_1', 'pre_advanced_2', 'advanced_1', 'advanced_2', 'advanced_plus_1', 'advanced_plus_2') NOT NULL,
#     id_professor INT,
#     nome_professor VARCHAR(255) NOT NULL,
    status ENUM('ATIVADO', 'DESATIVADO') NOT NULL ,
    id_turma int,
#     FOREIGN KEY (id_professor) REFERENCES tbl_profs(id_professor),
#     FOREIGN KEY (nome_professor) REFERENCES tbl_profs(nome_professor),
    FOREIGN KEY (id_turma) REFERENCES tbl_turmas(id_turma)

);





