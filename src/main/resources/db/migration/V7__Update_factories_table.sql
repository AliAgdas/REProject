SET client_encoding = 'UTF8';

ALTER TABLE public.factories
    ADD COLUMN now_recycle_kg double precision;
