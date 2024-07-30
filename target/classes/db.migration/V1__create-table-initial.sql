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
    id_professor INT,
    nome_professor VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_professor) REFERENCES tbl_profs(id_professor),
    FOREIGN KEY (nome_professor) REFERENCES tbl_profs(nome_professor)
);


CREATE TABLE tbl_profs
(
    id_professor   INT PRIMARY KEY AUTO_INCREMENT,
    nome_professor VARCHAR(255) NOT NULL,
    cpfCnpj varchar(40) not null UNIQUE,
    id_endereco CHAR(36) NOT NULL,
    email     VARCHAR(40)  NOT NULL,
    login     VARCHAR(40)  NOT NULL UNIQUE,
    password  VARCHAR(20)  NOT NULL,
    whatsapp  VARCHAR(13)  NOT NULL,
    roles     VARCHAR(10)  NOT NULL CHECK (roles IN ('ADMIN', 'PROF'))
);

# Adição de professores. Tirar quando for subir o DB
INSERT INTO tbl_profs (nome_professor, cpfCnpj, id_endereco, email, login, password, whatsapp, roles)
VALUES
    ('João Silva', '123.456.789-00', '1d2d3d4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 'joao.silva@example.com', 'joao123', 'senha1234', '11987654321', 'ADMIN'),

    ('Maria Oliveira', '987.654.321-00', '2d3e4f5g-6h7i-8j9k-0l1m-2n3o4p5q6r7s', 'maria.oliveira@example.com', 'maria456', 'senha5678', '21987654321', 'PROF'),

    ('Carlos Pereira', '111.222.333-44', '3e4f5g6h-7i8j-9k0l-1m2n-3o4p5q6r7s8t', 'carlos.pereira@example.com', 'carlos789', 'senha9012', '31987654321', 'PROF'),

    ('Ana Costa', '555.666.777-88', '4f5g6h7i-8j9k-0l1m-2n3o-4p5q6r7s8t9u', 'ana.costa@example.com', 'ana321', 'senha3456', '41987654321', 'ADMIN'),

    ('Roberto Almeida', '999.000.111-22', '5g6h7i8j-9k0l-1m2n-3o4p-5q6r7s8t9u0v', 'roberto.almeida@example.com', 'roberto654', 'senha7890', '51987654321', 'PROF');



#
# CREATE TABLE tbl_semana
# (
#     id_semana   INT PRIMARY KEY,
#     dia_inicial DATE,
#     dia_final   DATE
# );
#
# CREATE TABLE tbl_horario_semanal (
#     id_horario INT PRIMARY KEY AUTO_INCREMENT,
#     id_professor INT,
#     id_semana INT,
#     dia_semana VARCHAR(20),
#     horario_inicio TIME,
#     horario_fim TIME,
#     tipo_aula VARCHAR(20),
#     id_aluno_matricula CHAR(36),
#     FOREIGN KEY (id_professor) REFERENCES tbl_profs (id_profs),
#     FOREIGN KEY (id_semana) REFERENCES tbl_semana (id_semana),
#     FOREIGN KEY (id_aluno_matricula) REFERENCES tbl_alunos (id_aluno_matricula)
# );
#
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
#
# CREATE TABLE tbl_pacote_de_aulas (
#     id_pacote INT PRIMARY KEY,
#     id_aluno_matricula INT,
#     horas_de_aula INT,
#     conversation BOOLEAN,
#     pre_aula BOOLEAN,
#     pos_aula BOOLEAN,
#     FOREIGN KEY (id_aluno_matricula) REFERENCES tbl_alunos (id_aluno_matricula)
# );
#
#
# # Preenchimento da table semanas
#
# DELIMITER //
#
# CREATE PROCEDURE PreencherSemanasDoAno(ano INT)
# BEGIN
#     DECLARE diaInicio DATE;
#     DECLARE diaFinal DATE;
#     DECLARE proximoDia DATE;
#     DECLARE semana INT DEFAULT 1;
#
#     -- Encontrar a primeira segunda-feira do ano
#     SET diaInicio = CONCAT(ano, '-01-01');
#     WHILE WEEKDAY(diaInicio) != 0
#         DO
#             SET diaInicio = DATE_ADD(diaInicio, INTERVAL 1 DAY);
#         END WHILE;
#
#     SET proximoDia = diaInicio;
#
#     -- Limpar a tabela para o ano especificado, se necessário
#     DELETE FROM tbl_semana WHERE YEAR(dia_inicial) = ano;
#
#     -- Preencher as semanas
#     WHILE proximoDia <= CONCAT(ano, '-12-31')
#         DO
#             SET diaFinal = DATE_ADD(proximoDia, INTERVAL 5 DAY);
#             -- Sábado da mesma semana
#
#             -- Verificar se a última semana deve terminar em 31 de dezembro
#             IF diaFinal > CONCAT(ano, '-12-31') THEN
#                 SET diaFinal = CONCAT(ano, '-12-31');
#             END IF;
#
#             -- Inserir a semana na tabela
#             INSERT INTO tbl_semana (id_semana, dia_inicial, dia_final)
#             VALUES (LPAD(semana, 2, '0'), proximoDia, diaFinal);
#
#             -- Preparar para a próxima semana
#             SET proximoDia = DATE_ADD(diaFinal, INTERVAL 2 DAY); -- Ir para a próxima segunda-feira
#             SET semana = semana + 1;
#
#             -- Ajustar o início da próxima semana para a primeira segunda-feira após o fim da última semana
#             WHILE WEEKDAY(proximoDia) != 0
#                 DO
#                     SET proximoDia = DATE_ADD(proximoDia, INTERVAL 1 DAY);
#                 END WHILE;
#         END WHILE;
# END //
#
# DELIMITER ;
#
#
# # Criação do evento de preencher a table no próximo ano em novembro:
#
# CREATE EVENT IF NOT EXISTS PreencherSemanasProximoAno
#     ON SCHEDULE EVERY 1 YEAR
#         STARTS '2024-11-01 00:00:00'
#     DO CALL PreencherSemanasDoAno(YEAR(CURDATE()) + 1);
#
# CALL PreencherSemanasDoAno(2024);