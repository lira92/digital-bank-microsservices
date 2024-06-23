DROP TABLE IF EXISTS `conta`;
CREATE TABLE `conta`(
	`id` bigint NOT NULL AUTO_INCREMENT,
    `numero` bigint NOT NULL UNIQUE,
    `nome` varchar(255) NOT NULL,
    `cpf` varchar(255) NOT NULL UNIQUE,
    `email` varchar(255) NOT NULL UNIQUE,
    `saldo` double NOT NULL,
    `status` bit(1) NOT NULL,
    `telefone` varchar(255) NOT NULL UNIQUE,
    `data_de_nascimento` varchar(255) NOT NULL,
    `senha` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_numero` (`numero`),
    UNIQUE KEY `UK_cpf` (`cpf`),
    UNIQUE KEY `UK_email` (`email`)
);

DROP TABLE IF EXISTS `movimentacao`;
CREATE TABLE `movimentacao`(
	`id` bigint NOT NULL AUTO_INCREMENT,
    `nome` varchar(255) DEFAULT NULL,
    `valor` double NOT NULL,
    `datahora` DATETIME NOT NULL,
    `tipo` ENUM('DEBITO', 'CREDITO') NOT NULL,
    `conta_id` bigint NOT NULL,
    PRIMARY KEY(`id`),
    KEY `FK_conta_id` (`conta_id`),
    CONSTRAINT `FK_conta_id` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`)
);