CREATE TABLE tbl_alunos
(
    id_aluno_matricula INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    id_endereco CHAR(36) NOT NULL,
    data_de_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    formacao VARCHAR(255) NOT NULL,
    modulo_feito VARCHAR(255) NOT NULL,
    nivel VARCHAR(255) NOT NULL
);

# CREATE TABLE tbl_profs
# (
#     id_profs   INT PRIMARY KEY AUTO_INCREMENT,
#     nome_profs VARCHAR(255) NOT NULL,
#     email     VARCHAR(40)  NOT NULL,
#     login     VARCHAR(40)  NOT NULL UNIQUE,
#     password  VARCHAR(13)  NOT NULL,
#     whatsapp  VARCHAR(13)  NOT NULL,
#     roles     VARCHAR(10)  NOT NULL CHECK (roles IN ('ADMIN', 'PROF'))
# );
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