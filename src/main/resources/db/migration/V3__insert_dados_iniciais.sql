-- =========================
-- ATIVIDADES
-- =========================
INSERT INTO atividades (id, nome, descricao) VALUES
(1, 'Iniciação à Docência, Pesquisa e Extensão',
 'Atividades voltadas à iniciação acadêmica nas áreas de docência, pesquisa e extensão universitária.'),

(2, 'Atividades Artístico-Culturais e Esportivas',
 'Atividades relacionadas à cultura, artes e práticas esportivas reconhecidas pela instituição.'),

(3, 'Participação ou Organização de Eventos',
 'Participação e/ou organização de eventos acadêmicos, científicos, culturais ou institucionais.'),

(4, 'Experiências Ligadas à Formação Profissional',
 'Atividades que contribuem diretamente para a formação profissional ou áreas correlatas.'),

(5, 'Produção Técnica ou Científica',
 'Produção de trabalhos técnicos, científicos, artigos, relatórios ou materiais acadêmicos.'),

(6, 'Experiências de Gestão',
 'Atividades relacionadas à gestão acadêmica, administrativa ou representação institucional.'),

(7, 'Outras Atividades',
 'Atividades não enquadradas nas categorias anteriores, desde que reconhecidas pela instituição.');

-- =========================
-- SUBTIPOS — ATIVIDADE 1
-- =========================
INSERT INTO subtipos (id, descricao, horas_min, horas_max, atividade_id) VALUES
(1, 'Iniciação Científica', 0, 96, 1),
(2, 'Iniciação à Docência / Monitoria de Projetos', 0, 96, 1),
(3, 'Monitoria de Extensão', 0, 96, 1),
(4, 'Atuação como Facilitador em Célula de Aprendizagem Cooperativa', 0, 96, 1);

-- =========================
-- SUBTIPOS — ATIVIDADE 2
-- =========================
INSERT INTO subtipos (id, descricao, horas_min, horas_max, atividade_id) VALUES
(5, 'Audiência de Espetáculo Cultural', 1, 5, 2),
(6, 'Atuação em Atividade Cultural', 5, 40, 2),
(7, 'Produção de Atividade Cultural', 10, 80, 2),
(8, 'Prática Esportiva', 2, 48, 2),
(9, 'Participação em Torneio Esportivo', 8, 80, 2);

-- =========================
-- SUBTIPOS — ATIVIDADE 3
-- =========================
INSERT INTO subtipos (id, descricao, horas_min, horas_max, atividade_id) VALUES
(10, 'Assistir palestra, workshop ou congresso não correlatos ao curso', 0, 6, 3),
(11, 'Apresentar palestra, workshop ou congresso não correlatos ao curso', 0, 12, 3),
(12, 'Organizar palestra, workshop ou congresso não correlatos ao curso', 6, 24, 3),
(13, 'Assistir palestra, workshop ou congresso correlatos ao curso', 0, 8, 3),
(14, 'Apresentar palestra, workshop ou congresso correlatos ao curso', 0, 16, 3),
(15, 'Organizar palestra, workshop ou congresso correlatos ao curso', 6, 32, 3),
(16, 'Organização de Torneio Esportivo', 6, 24, 3);

-- =========================
-- SUBTIPOS — ATIVIDADE 4
-- =========================
INSERT INTO subtipos (id, descricao, horas_min, horas_max, atividade_id) VALUES
(17, 'Minicurso correlato ao curso', 0, 16, 4),
(18, 'Curso de aperfeiçoamento técnico correlato ao curso', 0, 32, 4),
(19, 'Certificação técnica correlata ao curso', 0, 32, 4),
(20, 'Visita técnica externa', 0, 8, 4),
(21, 'Vivência profissional', 4, 16, 4),
(22, 'Vivência profissional correlata ao curso', 8, 32, 4),
(23, 'Curso de idiomas', 8, 24, 4),
(24, 'TOEFL, IBT ou exame similar', 16, 24, 4),
(25, 'Docência nos ensinos fundamental e médio', 4, 16, 4);

-- =========================
-- SUBTIPOS — ATIVIDADE 5
-- =========================
INSERT INTO subtipos (id, descricao, horas_min, horas_max, atividade_id) VALUES
(26, 'Desenvolvimento de hardware ou software', 32, 96, 5),
(27, 'Publicação em evento nacional (exceto Encontros Universitários)', 4, 16, 5),
(28, 'Publicação em evento internacional', 8, 32, 5),
(29, 'Publicação em periódico nacional', 16, 64, 5),
(30, 'Publicação em periódico internacional', 24, 96, 5);

-- =========================
-- SUBTIPOS — ATIVIDADE 7
-- =========================
INSERT INTO subtipos (id, descricao, horas_min, horas_max, atividade_id) VALUES
(31, 'Voluntariado', 8, 48, 7),
(32, 'Avaliação Institucional da UFC', 1, 1, 7),
(33, 'Outras atividades não previstas neste regulamento', 0, 48, 7);

-- =========================
-- INSTITUICAO
-- =========================
INSERT INTO instituicoes (id, cnpj, endereco, nome)
VALUES (1, '07.272.636/0001-31', 'Rua Coronel Estanislau Frota, 563 - Bloco I - Centro - Campus de Sobral - Mucambinho', 'Universidade Federal do Ceará');

-- =========================
-- CURSO
-- =========================
INSERT INTO cursos (total_horas_complementares, id, nome) VALUES (176, 1, 'Engenharia da Computação');