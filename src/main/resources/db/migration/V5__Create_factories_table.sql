SET client_encoding = 'UTF8';

CREATE TABLE public.factories (
                                  factory_id integer NOT NULL,
                                  distance_km double precision NOT NULL
);

ALTER TABLE public.factories ALTER COLUMN factory_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.factories_factory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

ALTER TABLE ONLY public.factories ADD CONSTRAINT factories_pkey PRIMARY KEY (factory_id);