-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 17/06/2025 às 02:40
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `reservarecursos`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `funcao`
--

CREATE TABLE `funcao` (
  `idfuncao` int(11) NOT NULL,
  `tipoFuncao` varchar(45) NOT NULL,
  `permissao` enum('TEPT/GERENCIA','COORDENADOR/PROFESSOR/INSTRUTUTOR','EXTERNO','ADMINISTRATIVO') NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ativo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `funcao`
--

INSERT INTO `funcao` (`idfuncao`, `tipoFuncao`, `permissao`, `createdAt`, `updatedAt`, `ativo`) VALUES
(1, 'Coordenador Pedagógico', '', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(2, 'Instrutor Técnico', '', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(3, 'Gerente Administrativo', 'TEPT/GERENCIA', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(4, 'Colaborador Externo', 'EXTERNO', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(5, 'Analista Administrativo', 'ADMINISTRATIVO', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `reserva`
--

CREATE TABLE `reserva` (
  `idrecurso` int(11) NOT NULL,
  `idresponsavel` int(11) NOT NULL,
  `idtiporecurso` int(11) NOT NULL,
  `descricao` longtext DEFAULT NULL,
  `quantidade` tinyint(4) NOT NULL,
  `dataLocacao` date DEFAULT NULL,
  `horarioLocacao` time DEFAULT NULL,
  `dataDevolucao` date DEFAULT NULL,
  `horarioDevolucao` time DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ativo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `reserva`
--

INSERT INTO `reserva` (`idrecurso`, `idresponsavel`, `idtiporecurso`, `descricao`, `quantidade`, `dataLocacao`, `horarioLocacao`, `dataDevolucao`, `horarioDevolucao`, `createdAt`, `updatedAt`, `ativo`) VALUES
(1, 1, 1, 'Tudo certo, sem anomalias', 1, '2025-06-13', '08:00:00', '2025-06-13', '12:00:00', '2025-06-16 23:09:56', '2025-06-16 23:09:57', 1),
(2, 2, 2, 'Fiat Uno - Placa ABC1D23', 1, '2025-06-14', '13:00:00', '2025-06-13', '08:00:00', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(3, 3, 3, 'Datashow Epson X123     ', 1, '2025-06-15', '09:00:00', '2025-06-13', '08:00:00', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(4, 4, 4, 'Notebook Dell i5 8GB RAM', 2, '2025-06-16', '10:00:00', '2025-06-13', '08:00:00', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `responsavel`
--

CREATE TABLE `responsavel` (
  `idresponsavel` int(11) NOT NULL,
  `idfuncao` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cracha` varchar(10) DEFAULT NULL,
  `telefone` varchar(15) DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ativo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `responsavel`
--

INSERT INTO `responsavel` (`idresponsavel`, `idfuncao`, `nome`, `cracha`, `telefone`, `createdAt`, `updatedAt`, `ativo`) VALUES
(1, 1, 'Ana Paula Gomes', 'C001', '(44) 99888-0001', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(2, 2, 'Bruno Silva', 'C002', '(44) 99888-0002', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(3, 3, 'Carlos Eduardo', 'C003', '(44) 99888-0003', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(4, 4, 'Daniela Souza', 'C004', '(44) 99888-0004', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(5, 5, 'Elaine Martins', 'C005', '(44) 99888-0005', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `tiporecurso`
--

CREATE TABLE `tiporecurso` (
  `idtiporecurso` int(11) NOT NULL,
  `tiporecurso` varchar(45) NOT NULL,
  `descricao` longtext DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ativo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tiporecurso`
--

INSERT INTO `tiporecurso` (`idtiporecurso`, `tiporecurso`, `descricao`, `createdAt`, `updatedAt`, `ativo`) VALUES
(1, 'Sala', 'Sala de aula equipada com projetor e quadro branco', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(2, 'Carro', 'Veículo da instituição para uso administrativo e pedagógico', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(3, 'Datashow', 'Projetor multimídia para apresentações', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1),
(4, 'Notebook', 'Notebook para uso em atividades acadêmicas', '2025-06-16 23:09:56', '2025-06-16 23:09:56', 1);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `funcao`
--
ALTER TABLE `funcao`
  ADD PRIMARY KEY (`idfuncao`);

--
-- Índices de tabela `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`idrecurso`),
  ADD KEY `fk_recurso_responsavel` (`idresponsavel`),
  ADD KEY `fk_recurso_tiporecurso` (`idtiporecurso`);

--
-- Índices de tabela `responsavel`
--
ALTER TABLE `responsavel`
  ADD PRIMARY KEY (`idresponsavel`),
  ADD KEY `fk_responsavel_funcao` (`idfuncao`);

--
-- Índices de tabela `tiporecurso`
--
ALTER TABLE `tiporecurso`
  ADD PRIMARY KEY (`idtiporecurso`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `funcao`
--
ALTER TABLE `funcao`
  MODIFY `idfuncao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `reserva`
--
ALTER TABLE `reserva`
  MODIFY `idrecurso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `responsavel`
--
ALTER TABLE `responsavel`
  MODIFY `idresponsavel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `tiporecurso`
--
ALTER TABLE `tiporecurso`
  MODIFY `idtiporecurso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `fk_recurso_responsavel` FOREIGN KEY (`idresponsavel`) REFERENCES `responsavel` (`idresponsavel`),
  ADD CONSTRAINT `fk_recurso_tiporecurso` FOREIGN KEY (`idtiporecurso`) REFERENCES `tiporecurso` (`idtiporecurso`);

--
-- Restrições para tabelas `responsavel`
--
ALTER TABLE `responsavel`
  ADD CONSTRAINT `fk_responsavel_funcao` FOREIGN KEY (`idfuncao`) REFERENCES `funcao` (`idfuncao`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
