CREATE TABLE tbl_endereco (
                              id_endereco INT PRIMARY KEY AUTO_INCREMENT,
                              logradouro VARCHAR(255),
                              numero VARCHAR(255),
                              complemento VARCHAR(255),
                              bairro VARCHAR(255),
                              cidade VARCHAR(255),
                              estado VARCHAR(255),
                              cep VARCHAR(255)
);

CREATE TABLE tbl_profs (
                           id_professor INT PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           cpf VARCHAR(40) NOT NULL UNIQUE,
                           id_endereco INT,
                           email VARCHAR(40) NOT NULL,
                           whatsapp VARCHAR(13),
                           login VARCHAR(40) NOT NULL UNIQUE,
                           password VARCHAR(20) NOT NULL,
                           roles VARCHAR(10) NOT NULL CHECK (roles IN ('ADMIN', 'PROF')),
                           FOREIGN KEY (id_endereco) REFERENCES tbl_endereco(id_endereco)
);

CREATE TABLE tbl_turmas (
                            id_turma INT AUTO_INCREMENT PRIMARY KEY,
                            nomeTurma VARCHAR(100) NOT NULL,
                            id_professor INT,
                            FOREIGN KEY (id_professor) REFERENCES tbl_profs(id_professor)
);

CREATE TABLE tbl_horarios (
                              id_horario INT AUTO_INCREMENT PRIMARY KEY,
                              id_turma INT NULL,
                              dia_semana ENUM('SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO', 'DOMINGO') NOT NULL,
                              horario_inicio TIME NOT NULL,
                              horario_fim TIME NOT NULL,
                              FOREIGN KEY (id_turma) REFERENCES tbl_turmas(id_turma) ON DELETE SET NULL
);

CREATE TABLE tbl_alunos (
                            id_aluno_matricula INT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(255) NOT NULL,
                            cpf VARCHAR(40) NOT NULL UNIQUE,
                            id_endereco INT,
                            email VARCHAR(40) NOT NULL,
                            whatsapp VARCHAR(13),
                            data_de_nascimento DATE NOT NULL,
                            formacao VARCHAR(255) NOT NULL,
                            profissao VARCHAR(255) NOT NULL,
                            modulo_feito ENUM('nenhum', 'beginners_1', 'beginners_2', 'elementary_1', 'elementary_2', 'pre_intermediate_1', 'pre_intermediate_2', 'pre_intermediate_plus_1', 'pre_intermediate_plus_2', 'intermediate_1', 'intermediate_2', 'pre_advanced_1', 'pre_advanced_2', 'advanced_1', 'advanced_2', 'advanced_plus_1', 'advanced_plus_2') NOT NULL,
                            nivel ENUM('nenhum', 'beginners_1', 'beginners_2', 'elementary_1', 'elementary_2', 'pre_intermediate_1', 'pre_intermediate_2', 'pre_intermediate_plus_1', 'pre_intermediate_plus_2', 'intermediate_1', 'intermediate_2', 'pre_advanced_1', 'pre_advanced_2', 'advanced_1', 'advanced_2', 'advanced_plus_1', 'advanced_plus_2') NOT NULL,
                            status ENUM('ATIVADO', 'DESATIVADO') NOT NULL,
                            turma INT NULL,
                            responsavel INT NULL,
                            FOREIGN KEY (id_endereco) REFERENCES tbl_endereco(id_endereco),
                            FOREIGN KEY (turma) REFERENCES tbl_turmas(id_turma),
                            FOREIGN KEY (responsavel) REFERENCES tbl_responsaveis(id_responsavel)
);

CREATE TABLE tbl_responsaveis (
                                  id_responsavel INT AUTO_INCREMENT PRIMARY KEY,
                                  nome VARCHAR(255) NOT NULL,
                                  cpf VARCHAR(40) NOT NULL UNIQUE,
                                  id_endereco INT,
                                  email VARCHAR(40) NOT NULL,
                                  whatsapp VARCHAR(13),
                                  data_de_nascimento DATE NOT NULL,
                                  formacao VARCHAR(255) NOT NULL,
                                  profissao VARCHAR(255) NOT NULL,
                                  FOREIGN KEY (id_endereco) REFERENCES tbl_endereco(id_endereco)
);