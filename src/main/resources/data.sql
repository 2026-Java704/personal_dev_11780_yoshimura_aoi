-- カテゴリーテーブルにデータを挿入するクエリ
INSERT INTO categories  (name)
VALUES ('仕事'),('プライベート');

-- タスクテーブルにデータを挿入するクエリ
INSERT INTO tasks (category_id, user_id, title, deadline, importance, routine, memo, is_today)
VALUES (1, 1, 'プロジェクトA', '2026-5-29', 0, 1, 'A社との取引', TRUE),
(2, 2, 'プロジェクトB', '2026-5-22', 2, 0, 'B社との取引', FALSE);

-- ユーザーテーブルにデータを挿入するクエリ
INSERT INTO users  (name, email, password, last_login_date)
VALUES ('田中太郎', 'tanaka@aaa.com', 'himitu', '2026-5-29'),('鈴木花子', 'suzuki@aaa.com', 'himitsu', '2026-5-28');