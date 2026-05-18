-- Agrega columnas de material a la tabla recuento

ALTER TABLE recuento
  ADD COLUMN n_items_creados BIGINT NOT NULL DEFAULT 0,
  ADD COLUMN n_materiales_creados BIGINT NOT NULL DEFAULT 0;
