INSERT INTO users (name, password)
 VALUES ('鈴木一郎', 'himitu'), ('佐藤悠介', 'okusuri'), ('田中愛子', 'check');

 INSERT INTO medicine (name, drink_time, around, note, count, m_check, users_id)
  VALUES
  ('風邪薬', '朝','食後', '食後1回2錠', 2, FALSE, 1),
  ('頭痛薬', '朝','食後', '食後1回1錠', 1, FALSE, 1),
   ('イブプロフェン', '朝','食後', '食後1回1錠', 1, FALSE, 2),
    ('ビオフェルミン', '朝','食前', '食後1回3錠', 3, FALSE, 3),
     ('ロキソニン', '朝','食後', '食後1回2錠', 2, FALSE, 1);