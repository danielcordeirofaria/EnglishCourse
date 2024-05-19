CREATE TABLE alunos
(
    idAluno          CHAR(36) PRIMARY KEY,
    nome             VARCHAR(255),
    matricula        VARCHAR(255) UNIQUE,
    endereco_id      CHAR(36),
    dataDeNascimento DATE,
    cpf              VARCHAR(14) UNIQUE,
    email            VARCHAR(255),
    formacao         VARCHAR(255),
    moduloFeito      VARCHAR(255),
    nivel            VARCHAR(255),
    pacoteDeAulas_id CHAR(36)
);

CREATE TABLE tbl_profs
(
    idProfs   INT PRIMARY KEY AUTO_INCREMENT,
    nomeProfs VARCHAR(255) NOT NULL,
    email     VARCHAR(40)  NOT NULL,
    login     VARCHAR(40)  NOT NULL UNIQUE,
    password  TEXT         NOT NULL, -- Assumindo que seja uma string hash
    whatsapp  VARCHAR(13)  NOT NULL,
    roles     VARCHAR(10)  NOT NULL CHECK (roles IN ('ADMIN', 'PROF'))
);

CREATE TABLE semana
(
    idSemana   VARCHAR(10) PRIMARY KEY,
    diaInicial DATE,
    diaFinal   DATE
);

CREATE TABLE horarioSemanal
(
    idHorarioAula CHAR(36) PRIMARY KEY,
    tipoAula      VARCHAR(255),
    dataInit      TIME,
    tempoDeAula   INT,
    aluno_id      CHAR(36),
    profs_id      INT,
    semana_id     VARCHAR(10),
    FOREIGN KEY (aluno_id) REFERENCES alunos (idAluno),
    FOREIGN KEY (profs_id) REFERENCES tbl_profs (idProfs),
    FOREIGN KEY (semana_id) REFERENCES semana (idSemana)
);

CREATE TABLE endereco
(
    idEndereco  CHAR(36) PRIMARY KEY,
    aluno_id    CHAR(36),
    rua         VARCHAR(255),
    numero      VARCHAR(255),
    complemento VARCHAR(255),
    bairro      VARCHAR(255),
    cidade      VARCHAR(255),
    estado      VARCHAR(255),
    cep         VARCHAR(255),
    FOREIGN KEY (aluno_id) REFERENCES alunos (idAluno)
);


# Preenchimento da table semanas

DELIMITER //

CREATE PROCEDURE PreencherSemanasDoAno(ano INT)
BEGIN
    DECLARE diaInicio DATE;
    DECLARE diaFinal DATE;
    DECLARE proximoDia DATE;
    DECLARE semana INT DEFAULT 1;

    -- Encontrar a primeira segunda-feira do ano
    SET diaInicio = CONCAT(ano, '-01-01');
    WHILE WEEKDAY(diaInicio) != 0
        DO
            SET diaInicio = DATE_ADD(diaInicio, INTERVAL 1 DAY);
        END WHILE;

    SET proximoDia = diaInicio;

    -- Limpar a tabela para o ano especificado, se necessário
    DELETE FROM semana WHERE YEAR(diaInicial) = ano;

    -- Preencher as semanas
    WHILE proximoDia <= CONCAT(ano, '-12-31')
        DO
            SET diaFinal = DATE_ADD(proximoDia, INTERVAL 5 DAY);
            -- Sábado da mesma semana

            -- Verificar se a última semana deve terminar em 31 de dezembro
            IF diaFinal > CONCAT(ano, '-12-31') THEN
                SET diaFinal = CONCAT(ano, '-12-31');
            END IF;

            -- Inserir a semana na tabela
            INSERT INTO semana (idSemana, diaInicial, diaFinal)
            VALUES (LPAD(semana, 2, '0'), proximoDia, diaFinal);

            -- Preparar para a próxima semana
            SET proximoDia = DATE_ADD(diaFinal, INTERVAL 2 DAY); -- Ir para a próxima segunda-feira
            SET semana = semana + 1;

            -- Ajustar o início da próxima semana para a primeira segunda-feira após o fim da última semana
            WHILE WEEKDAY(proximoDia) != 0
                DO
                    SET proximoDia = DATE_ADD(proximoDia, INTERVAL 1 DAY);
                END WHILE;
        END WHILE;
END //

DELIMITER ;


# Criação do evento de preencher a table no próximo ano em novembro:

CREATE EVENT IF NOT EXISTS PreencherSemanasProximoAno
    ON SCHEDULE EVERY 1 YEAR
        STARTS '2024-11-01 00:00:00'
    DO CALL PreencherSemanasDoAno(YEAR(CURDATE()) + 1);

CALL PreencherSemanasDoAno(2024);