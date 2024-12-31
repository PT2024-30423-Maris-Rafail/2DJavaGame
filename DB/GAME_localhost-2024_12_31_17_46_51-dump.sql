--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: tilecollision; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tilecollision WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


ALTER DATABASE tilecollision OWNER TO postgres;

\connect tilecollision

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Collisions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Collisions" (
    name character varying(100) NOT NULL,
    collision boolean NOT NULL
);


ALTER TABLE public."Collisions" OWNER TO postgres;

--
-- Name: country; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.country (
    id integer NOT NULL,
    country_name character varying(50) NOT NULL
);


ALTER TABLE public.country OWNER TO postgres;

--
-- Name: country_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.country_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.country_id_seq OWNER TO postgres;

--
-- Name: country_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.country_id_seq OWNED BY public.country.id;


--
-- Name: locations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.locations (
    id integer NOT NULL,
    coordinates character varying(40) NOT NULL,
    checked boolean DEFAULT false NOT NULL,
    country_id integer NOT NULL
);


ALTER TABLE public.locations OWNER TO postgres;

--
-- Name: locations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.locations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.locations_id_seq OWNER TO postgres;

--
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.locations_id_seq OWNED BY public.locations.id;


--
-- Name: mapforobj; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mapforobj (
    name character varying(50) NOT NULL,
    mapid integer NOT NULL,
    collision boolean NOT NULL
);


ALTER TABLE public.mapforobj OWNER TO postgres;

--
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    message text,
    id integer NOT NULL
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.messages_id_seq OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;


--
-- Name: scores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scores (
    score_id integer NOT NULL,
    hours integer DEFAULT 0 NOT NULL,
    minutes integer DEFAULT 0 NOT NULL,
    seconds integer DEFAULT 0 NOT NULL,
    milliseconds integer DEFAULT 0 NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.scores OWNER TO postgres;

--
-- Name: scores_score_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.scores_score_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.scores_score_id_seq OWNER TO postgres;

--
-- Name: scores_score_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.scores_score_id_seq OWNED BY public.scores.score_id;


--
-- Name: tips; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tips (
    id integer NOT NULL,
    tips text NOT NULL,
    active_seconds integer
);


ALTER TABLE public.tips OWNER TO postgres;

--
-- Name: tips_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tips_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tips_id_seq OWNER TO postgres;

--
-- Name: tips_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tips_id_seq OWNED BY public.tips.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(80) NOT NULL,
    email character varying(100) NOT NULL,
    bestrunid integer DEFAULT 0 NOT NULL,
    CONSTRAINT users_email_check CHECK (((email)::text ~~ '%_@_%.__%'::text))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: country id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.country ALTER COLUMN id SET DEFAULT nextval('public.country_id_seq'::regclass);


--
-- Name: locations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations ALTER COLUMN id SET DEFAULT nextval('public.locations_id_seq'::regclass);


--
-- Name: messages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);


--
-- Name: scores score_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores ALTER COLUMN score_id SET DEFAULT nextval('public.scores_score_id_seq'::regclass);


--
-- Name: tips id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tips ALTER COLUMN id SET DEFAULT nextval('public.tips_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: Collisions; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."Collisions" VALUES ('void1.png', true);
INSERT INTO public."Collisions" VALUES ('voidBrick1.png', false);
INSERT INTO public."Collisions" VALUES ('voidBricDamaged1.png', true);
INSERT INTO public."Collisions" VALUES ('voidBricDamaged2.png', true);
INSERT INTO public."Collisions" VALUES ('voidBricDamaged3.png', true);
INSERT INTO public."Collisions" VALUES ('0brick1.png', true);
INSERT INTO public."Collisions" VALUES ('1grass1.png', false);
INSERT INTO public."Collisions" VALUES ('2grassOF.png', false);
INSERT INTO public."Collisions" VALUES ('3grassPF.png', false);
INSERT INTO public."Collisions" VALUES ('4grassYF.png', false);
INSERT INTO public."Collisions" VALUES ('5path1.png', false);
INSERT INTO public."Collisions" VALUES ('6path2.png', false);
INSERT INTO public."Collisions" VALUES ('7path3.png', false);
INSERT INTO public."Collisions" VALUES ('8path4.png', false);
INSERT INTO public."Collisions" VALUES ('water1.png', true);
INSERT INTO public."Collisions" VALUES ('voidBrickVoidLeft.png', false);
INSERT INTO public."Collisions" VALUES ('voidBrickVoidRight.png', false);
INSERT INTO public."Collisions" VALUES ('Black.png', false);
INSERT INTO public."Collisions" VALUES ('Zgate.png', false);
INSERT INTO public."Collisions" VALUES ('ZZDoorY.png', false);
INSERT INTO public."Collisions" VALUES ('ZZvoid2.png', false);
INSERT INTO public."Collisions" VALUES ('ZZZDivBri.png', false);
INSERT INTO public."Collisions" VALUES ('ZZZDivPat.png', false);
INSERT INTO public."Collisions" VALUES ('ZZZvoidBrick2.png', false);
INSERT INTO public."Collisions" VALUES ('zwall1.png', true);
INSERT INTO public."Collisions" VALUES ('zwall2.png', true);
INSERT INTO public."Collisions" VALUES ('zwall3.png', true);
INSERT INTO public."Collisions" VALUES ('zz1grass1.png', false);


--
-- Data for Name: country; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.country VALUES (1, 'Afghanistan');
INSERT INTO public.country VALUES (2, 'Albania');
INSERT INTO public.country VALUES (3, 'Algeria');
INSERT INTO public.country VALUES (4, 'Andorra');
INSERT INTO public.country VALUES (5, 'Angola');
INSERT INTO public.country VALUES (6, 'Antigua and Barbuda');
INSERT INTO public.country VALUES (7, 'Argentina');
INSERT INTO public.country VALUES (8, 'Armenia');
INSERT INTO public.country VALUES (9, 'Australia');
INSERT INTO public.country VALUES (10, 'Austria');
INSERT INTO public.country VALUES (11, 'Azerbaijan');
INSERT INTO public.country VALUES (12, 'Bahamas');
INSERT INTO public.country VALUES (13, 'Bahrain');
INSERT INTO public.country VALUES (14, 'Bangladesh');
INSERT INTO public.country VALUES (15, 'Barbados');
INSERT INTO public.country VALUES (16, 'Belarus');
INSERT INTO public.country VALUES (17, 'Belgium');
INSERT INTO public.country VALUES (18, 'Belize');
INSERT INTO public.country VALUES (19, 'Benin');
INSERT INTO public.country VALUES (20, 'Bhutan');
INSERT INTO public.country VALUES (21, 'Bolivia');
INSERT INTO public.country VALUES (22, 'Bosnia and Herzegovina');
INSERT INTO public.country VALUES (23, 'Botswana');
INSERT INTO public.country VALUES (24, 'Brazil');
INSERT INTO public.country VALUES (25, 'Brunei');
INSERT INTO public.country VALUES (26, 'Bulgaria');
INSERT INTO public.country VALUES (27, 'Burkina Faso');
INSERT INTO public.country VALUES (28, 'Burundi');
INSERT INTO public.country VALUES (29, 'Cambodia');
INSERT INTO public.country VALUES (30, 'Cameroon');
INSERT INTO public.country VALUES (31, 'Canada');
INSERT INTO public.country VALUES (32, 'Cape Verde');
INSERT INTO public.country VALUES (33, 'Central African Republic');
INSERT INTO public.country VALUES (34, 'Chad');
INSERT INTO public.country VALUES (35, 'Chile');
INSERT INTO public.country VALUES (36, 'China');
INSERT INTO public.country VALUES (37, 'Colombia');
INSERT INTO public.country VALUES (38, 'Comoros');
INSERT INTO public.country VALUES (39, 'Congo');
INSERT INTO public.country VALUES (40, 'Costa Rica');
INSERT INTO public.country VALUES (41, 'Croatia');
INSERT INTO public.country VALUES (42, 'Cuba');
INSERT INTO public.country VALUES (43, 'Cyprus');
INSERT INTO public.country VALUES (44, 'Czech Republic');
INSERT INTO public.country VALUES (45, 'Denmark');
INSERT INTO public.country VALUES (46, 'Djibouti');
INSERT INTO public.country VALUES (47, 'Dominica');
INSERT INTO public.country VALUES (48, 'Dominican Republic');
INSERT INTO public.country VALUES (49, 'East Timor');
INSERT INTO public.country VALUES (50, 'Ecuador');
INSERT INTO public.country VALUES (51, 'Egypt');
INSERT INTO public.country VALUES (52, 'El Salvador');
INSERT INTO public.country VALUES (53, 'Equatorial Guinea');
INSERT INTO public.country VALUES (54, 'Eritrea');
INSERT INTO public.country VALUES (55, 'Estonia');
INSERT INTO public.country VALUES (56, 'Eswatini');
INSERT INTO public.country VALUES (57, 'Ethiopia');
INSERT INTO public.country VALUES (58, 'Fiji');
INSERT INTO public.country VALUES (59, 'Finland');
INSERT INTO public.country VALUES (60, 'France');
INSERT INTO public.country VALUES (61, 'Gabon');
INSERT INTO public.country VALUES (62, 'Gambia');
INSERT INTO public.country VALUES (63, 'Georgia');
INSERT INTO public.country VALUES (64, 'Germany');
INSERT INTO public.country VALUES (65, 'Ghana');
INSERT INTO public.country VALUES (66, 'Greece');
INSERT INTO public.country VALUES (67, 'Grenada');
INSERT INTO public.country VALUES (68, 'Guatemala');
INSERT INTO public.country VALUES (69, 'Guinea');
INSERT INTO public.country VALUES (70, 'Guinea-Bissau');
INSERT INTO public.country VALUES (71, 'Guyana');
INSERT INTO public.country VALUES (72, 'Haiti');
INSERT INTO public.country VALUES (73, 'Honduras');
INSERT INTO public.country VALUES (74, 'Hungary');
INSERT INTO public.country VALUES (75, 'Iceland');
INSERT INTO public.country VALUES (76, 'India');
INSERT INTO public.country VALUES (77, 'Indonesia');
INSERT INTO public.country VALUES (78, 'Iran');
INSERT INTO public.country VALUES (79, 'Iraq');
INSERT INTO public.country VALUES (80, 'Ireland');
INSERT INTO public.country VALUES (81, 'Israel');
INSERT INTO public.country VALUES (82, 'Italy');
INSERT INTO public.country VALUES (83, 'Jamaica');
INSERT INTO public.country VALUES (84, 'Japan');
INSERT INTO public.country VALUES (85, 'Jordan');
INSERT INTO public.country VALUES (86, 'Kazakhstan');
INSERT INTO public.country VALUES (87, 'Kenya');
INSERT INTO public.country VALUES (88, 'Kiribati');
INSERT INTO public.country VALUES (89, 'Kuwait');
INSERT INTO public.country VALUES (90, 'Kyrgyzstan');
INSERT INTO public.country VALUES (91, 'Laos');
INSERT INTO public.country VALUES (92, 'Latvia');
INSERT INTO public.country VALUES (93, 'Lebanon');
INSERT INTO public.country VALUES (94, 'Lesotho');
INSERT INTO public.country VALUES (95, 'Liberia');
INSERT INTO public.country VALUES (96, 'Libya');
INSERT INTO public.country VALUES (97, 'Liechtenstein');
INSERT INTO public.country VALUES (98, 'Lithuania');
INSERT INTO public.country VALUES (99, 'Luxembourg');
INSERT INTO public.country VALUES (100, 'Madagascar');
INSERT INTO public.country VALUES (101, 'Malawi');
INSERT INTO public.country VALUES (102, 'Malaysia');
INSERT INTO public.country VALUES (103, 'Maldives');
INSERT INTO public.country VALUES (104, 'Mali');
INSERT INTO public.country VALUES (105, 'Malta');
INSERT INTO public.country VALUES (106, 'Marshall Islands');
INSERT INTO public.country VALUES (107, 'Mauritania');
INSERT INTO public.country VALUES (108, 'Mauritius');
INSERT INTO public.country VALUES (109, 'Mexico');
INSERT INTO public.country VALUES (110, 'Micronesia');
INSERT INTO public.country VALUES (111, 'Moldova');
INSERT INTO public.country VALUES (112, 'Monaco');
INSERT INTO public.country VALUES (113, 'Mongolia');
INSERT INTO public.country VALUES (114, 'Montenegro');
INSERT INTO public.country VALUES (115, 'Morocco');
INSERT INTO public.country VALUES (116, 'Mozambique');
INSERT INTO public.country VALUES (117, 'Myanmar');
INSERT INTO public.country VALUES (118, 'Namibia');
INSERT INTO public.country VALUES (119, 'Nauru');
INSERT INTO public.country VALUES (120, 'Nepal');
INSERT INTO public.country VALUES (121, 'Netherlands');
INSERT INTO public.country VALUES (122, 'New Zealand');
INSERT INTO public.country VALUES (123, 'Nicaragua');
INSERT INTO public.country VALUES (124, 'Niger');
INSERT INTO public.country VALUES (125, 'Nigeria');
INSERT INTO public.country VALUES (126, 'North Korea');
INSERT INTO public.country VALUES (127, 'North Macedonia');
INSERT INTO public.country VALUES (128, 'Norway');
INSERT INTO public.country VALUES (129, 'Oman');
INSERT INTO public.country VALUES (130, 'Pakistan');
INSERT INTO public.country VALUES (131, 'Palau');
INSERT INTO public.country VALUES (132, 'Palestine');
INSERT INTO public.country VALUES (133, 'Panama');
INSERT INTO public.country VALUES (134, 'Papua New Guinea');
INSERT INTO public.country VALUES (135, 'Paraguay');
INSERT INTO public.country VALUES (136, 'Peru');
INSERT INTO public.country VALUES (137, 'Philippines');
INSERT INTO public.country VALUES (138, 'Poland');
INSERT INTO public.country VALUES (139, 'Portugal');
INSERT INTO public.country VALUES (140, 'Qatar');
INSERT INTO public.country VALUES (141, 'Romania');
INSERT INTO public.country VALUES (142, 'Russia');
INSERT INTO public.country VALUES (143, 'Rwanda');
INSERT INTO public.country VALUES (144, 'Saint Kitts and Nevis');
INSERT INTO public.country VALUES (145, 'Saint Lucia');
INSERT INTO public.country VALUES (146, 'Saint Vincent and the Grenadines');
INSERT INTO public.country VALUES (147, 'Samoa');
INSERT INTO public.country VALUES (148, 'San Marino');
INSERT INTO public.country VALUES (149, 'Sao Tome and Principe');
INSERT INTO public.country VALUES (150, 'Saudi Arabia');
INSERT INTO public.country VALUES (151, 'Senegal');
INSERT INTO public.country VALUES (152, 'Serbia');
INSERT INTO public.country VALUES (153, 'Seychelles');
INSERT INTO public.country VALUES (154, 'Sierra Leone');
INSERT INTO public.country VALUES (155, 'Singapore');
INSERT INTO public.country VALUES (156, 'Slovakia');
INSERT INTO public.country VALUES (157, 'Slovenia');
INSERT INTO public.country VALUES (158, 'Solomon Islands');
INSERT INTO public.country VALUES (159, 'Somalia');
INSERT INTO public.country VALUES (160, 'South Africa');
INSERT INTO public.country VALUES (161, 'South Korea');
INSERT INTO public.country VALUES (162, 'South Sudan');
INSERT INTO public.country VALUES (163, 'Spain');
INSERT INTO public.country VALUES (164, 'Sri Lanka');
INSERT INTO public.country VALUES (165, 'Sudan');
INSERT INTO public.country VALUES (166, 'Suriname');
INSERT INTO public.country VALUES (167, 'Sweden');
INSERT INTO public.country VALUES (168, 'Switzerland');
INSERT INTO public.country VALUES (169, 'Syria');
INSERT INTO public.country VALUES (170, 'Tajikistan');
INSERT INTO public.country VALUES (171, 'Tanzania');
INSERT INTO public.country VALUES (172, 'Thailand');
INSERT INTO public.country VALUES (173, 'Togo');
INSERT INTO public.country VALUES (174, 'Tonga');
INSERT INTO public.country VALUES (175, 'Trinidad and Tobago');
INSERT INTO public.country VALUES (176, 'Tunisia');
INSERT INTO public.country VALUES (177, 'Turkey');
INSERT INTO public.country VALUES (178, 'Turkmenistan');
INSERT INTO public.country VALUES (179, 'Tuvalu');
INSERT INTO public.country VALUES (180, 'Uganda');
INSERT INTO public.country VALUES (181, 'Ukraine');
INSERT INTO public.country VALUES (182, 'United Arab Emirates');
INSERT INTO public.country VALUES (183, 'United Kingdom');
INSERT INTO public.country VALUES (184, 'United States');
INSERT INTO public.country VALUES (185, 'Uruguay');
INSERT INTO public.country VALUES (186, 'Uzbekistan');
INSERT INTO public.country VALUES (187, 'Vanuatu');
INSERT INTO public.country VALUES (188, 'Vatican City');
INSERT INTO public.country VALUES (189, 'Venezuela');
INSERT INTO public.country VALUES (190, 'Vietnam');
INSERT INTO public.country VALUES (191, 'Yemen');
INSERT INTO public.country VALUES (192, 'Zambia');
INSERT INTO public.country VALUES (193, 'Zimbabwe');


--
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.locations VALUES (3, '35.659088,139.700470', false, 84);
INSERT INTO public.locations VALUES (5, '41.5255186,19.7865759', false, 2);
INSERT INTO public.locations VALUES (9, '40.4565999,44.5846921', false, 8);
INSERT INTO public.locations VALUES (8, '-31.5226497,-68.5258047', false, 7);
INSERT INTO public.locations VALUES (2, '46.139759,23.387828', false, 141);
INSERT INTO public.locations VALUES (15, '51.5016809,-0.1411772', false, 183);
INSERT INTO public.locations VALUES (11, '42.8724837,74.4287206', false, 90);
INSERT INTO public.locations VALUES (4, '34.832884,67.7727754', false, 1);
INSERT INTO public.locations VALUES (1, '46.074778,23.565889', false, 141);
INSERT INTO public.locations VALUES (7, '42.5593881,1.6860208', false, 4);
INSERT INTO public.locations VALUES (13, '-21.9055729,35.3169916', false, 116);
INSERT INTO public.locations VALUES (16, '41.8898212,12.4934667', false, 82);
INSERT INTO public.locations VALUES (14, '48.8583154,2.2943783', false, 60);
INSERT INTO public.locations VALUES (10, '-19.2977168,135.0360727', false, 9);
INSERT INTO public.locations VALUES (12, '25.1910386,56.2317835', false, 182);
INSERT INTO public.locations VALUES (6, '29.5346937,0.183419', false, 3);


--
-- Data for Name: mapforobj; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.mapforobj VALUES ('map5', 2, false);
INSERT INTO public.mapforobj VALUES ('map1', 2, false);
INSERT INTO public.mapforobj VALUES ('map6', 3, false);
INSERT INTO public.mapforobj VALUES ('map4', 2, false);
INSERT INTO public.mapforobj VALUES ('map3', 2, false);
INSERT INTO public.mapforobj VALUES ('map2', 2, false);
INSERT INTO public.mapforobj VALUES ('Compass.png', 1, false);
INSERT INTO public.mapforobj VALUES ('DoorN.png', 1, true);
INSERT INTO public.mapforobj VALUES ('DoorY.png', 1, false);


--
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.messages VALUES ('Crazy? I was crazy once', 1);
INSERT INTO public.messages VALUES ('Nah, I''d geo-guess', 2);
INSERT INTO public.messages VALUES ('Ambience++', 3);
INSERT INTO public.messages VALUES ('Miles, the BLACK ONE', 4);
INSERT INTO public.messages VALUES ('You''re stupid. Stop being stupid.', 5);
INSERT INTO public.messages VALUES ('Un éléphant qui se balançait', 6);
INSERT INTO public.messages VALUES ('Pineapple... ON PIZZA??? ', 7);
INSERT INTO public.messages VALUES ('Un trandafir creste la firida mea', 8);
INSERT INTO public.messages VALUES ('They''re taking the hobbits to Isengard!', 9);
INSERT INTO public.messages VALUES ('The potato incident', 10);
INSERT INTO public.messages VALUES ('In the embrace of Messmer''s flame', 11);
INSERT INTO public.messages VALUES ('Fly, you fools!', 13);
INSERT INTO public.messages VALUES ('Now do it in assembly', 14);
INSERT INTO public.messages VALUES ('"UTCN is killing us"- Sun Tzu', 15);
INSERT INTO public.messages VALUES ('What do ghosts have to do with this?', 16);
INSERT INTO public.messages VALUES ('Your majesty, a second bus hit the bridge...', 17);
INSERT INTO public.messages VALUES ('Officer, I dropkicked that child in self defence.', 18);
INSERT INTO public.messages VALUES ('Fool of a took', 19);
INSERT INTO public.messages VALUES ('It''s quite simple redstone, really', 21);
INSERT INTO public.messages VALUES ('To be is to do -> Do be do be doo', 20);
INSERT INTO public.messages VALUES ('4rd worst thing to ever happen to those orphans', 12);
INSERT INTO public.messages VALUES ('All these squares make a circle...', 22);
INSERT INTO public.messages VALUES ('I''ll tell you where you''re not... Safe', 23);
INSERT INTO public.messages VALUES ('Why was 10 afraid? It was in the middle of 9-...', 24);


--
-- Data for Name: scores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.scores VALUES (157, 0, 0, 0, 0, 4);
INSERT INTO public.scores VALUES (160, 0, 3, 22, 19, 23);
INSERT INTO public.scores VALUES (162, 0, 0, 38, 81, 21);
INSERT INTO public.scores VALUES (163, 0, 0, 26, 94, 1);
INSERT INTO public.scores VALUES (164, 0, 0, 20, 4, 22);
INSERT INTO public.scores VALUES (165, 0, 0, 19, 47, 24);
INSERT INTO public.scores VALUES (166, 0, 0, 21, 77, 20);
INSERT INTO public.scores VALUES (167, 0, 0, 19, 8, 18);
INSERT INTO public.scores VALUES (168, 0, 0, 18, 45, 27);
INSERT INTO public.scores VALUES (170, 0, 0, 6, 30, 4);
INSERT INTO public.scores VALUES (18, 0, 5, 3, 44, 2);
INSERT INTO public.scores VALUES (19, 0, 4, 4, 54, 3);
INSERT INTO public.scores VALUES (171, 0, 1, 8, 71, 4);
INSERT INTO public.scores VALUES (172, 0, 1, 55, 35, 4);
INSERT INTO public.scores VALUES (22, 0, 2, 22, 0, 10);
INSERT INTO public.scores VALUES (25, 0, 4, 53, 99, 14);
INSERT INTO public.scores VALUES (27, 0, 2, 22, 12, 16);


--
-- Data for Name: tips; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tips VALUES (2, 'To return to your old self, head south', 5);
INSERT INTO public.tips VALUES (1, 'If you want to start anew, head north', 5);
INSERT INTO public.tips VALUES (4, 'Somewhere, a great rune has broken', 5);
INSERT INTO public.tips VALUES (3, 'I wonder where the key is...', -2);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (2, 'DocM77', '$2a$10$D.n5H99aeTLRPCMXOkbiG.9tNzWTtX4tkGV8URXbAdWixLJHaJpkm', 'pisica@gmail.com', 18);
INSERT INTO public.users VALUES (14, 'a', '$2a$10$Ye9/qUi/nP2IyZPgUgYcJOWQR9NHRLWL2XANQy4j6kcklLLlQEYHe', '123@yahoo.com', 25);
INSERT INTO public.users VALUES (4, 'admin', '$2a$10$xUefbTipw7R/5z0kXDpcy.p3suzPuc7ezzBcqNnt5C0PhKlGZUSD.', 'admin@gmail.com', 157);
INSERT INTO public.users VALUES (16, 'Rafa', '$2a$10$i4ioD8T0PStCu77XxqnsQugW1tlMsThsOE1U6hFkGd9SB4caRWoPW', 'TBND@raf.com', 27);
INSERT INTO public.users VALUES (10, 'Mira', '$2a$10$Db8aJhAMFA8G/T4ekF0reeNf.wK//zmQXq0qh0Rsq2aOb9TuV5C6q', 'mirabelamaris3@gmail.com', 22);
INSERT INTO public.users VALUES (3, 'Grian', '$2a$10$t/E2OKN6iiNBXlgfnqmloOsh6R8sxH43K2aho.QF6l8.SplPzbKKW', 'poultry@guy.com', 19);
INSERT INTO public.users VALUES (23, 'cioara', '$2a$10$RumlHIowzZ9jWF6vDxZ/6O9F1NV5ED1ufviiGmRQJBmoSG2LtrvEi', 'nigg@ger.com', 160);
INSERT INTO public.users VALUES (21, '1', '$2a$10$RumlHIowzZ9jWF6vDxZ/6O9F1NV5ED1ufviiGmRQJBmoSG2LtrvEi', 'ana@gmail.com', 162);
INSERT INTO public.users VALUES (1, 'MumboJumbo', '$2a$10$cz6LPzp744qvLJsbuakV7OTe/gCuKE7F7PTQ0KxxlfCjjDaWW2Hca', 'inginerul@yahoo.com', 163);
INSERT INTO public.users VALUES (22, '23', '$2a$10$RumlHIowzZ9jWF6vDxZ/6O9F1NV5ED1ufviiGmRQJBmoSG2LtrvEi', 'chinezu@cioara.com', 164);
INSERT INTO public.users VALUES (24, '111', '$2a$10$RumlHIowzZ9jWF6vDxZ/6O9F1NV5ED1ufviiGmRQJBmoSG2LtrvEi', 'aa@aa.com', 165);
INSERT INTO public.users VALUES (20, 'DocM7', '$2a$10$RumlHIowzZ9jWF6vDxZ/6O9F1NV5ED1ufviiGmRQJBmoSG2LtrvEi', 'pisica@google.com', 166);
INSERT INTO public.users VALUES (18, 'măr', '$2a$10$/mN9mkBjamZyEJ9gfBi8E.ZYBm6a6eBNLqhdaS/ozGWsqLwzUDJCe', 'maria@hcc.ro', 167);
INSERT INTO public.users VALUES (27, 'test', '$2a$10$fygjbS/b4Wd.uLj5o.C.tO2EcvFt6AKmyrwoBwFHGb7wBN81b9cxO', 'test@ghb.ce', 168);


--
-- Name: country_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.country_id_seq', 193, true);


--
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.locations_id_seq', 16, true);


--
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_id_seq', 24, true);


--
-- Name: scores_score_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.scores_score_id_seq', 172, true);


--
-- Name: tips_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tips_id_seq', 5, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 27, true);


--
-- Name: country country_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pk PRIMARY KEY (id);


--
-- Name: country country_pk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pk_2 UNIQUE (country_name);


--
-- Name: country country_pk_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pk_3 UNIQUE (country_name);


--
-- Name: locations locations_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations
    ADD CONSTRAINT locations_pk PRIMARY KEY (id);


--
-- Name: locations locations_pk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations
    ADD CONSTRAINT locations_pk_2 UNIQUE (coordinates);


--
-- Name: locations locations_pk_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.locations
    ADD CONSTRAINT locations_pk_3 UNIQUE (coordinates);


--
-- Name: mapforobj mapforobj_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mapforobj
    ADD CONSTRAINT mapforobj_pk UNIQUE (name);


--
-- Name: mapforobj mapforobj_pk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mapforobj
    ADD CONSTRAINT mapforobj_pk_2 UNIQUE (name);


--
-- Name: messages messages_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pk PRIMARY KEY (id);


--
-- Name: messages messages_pk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pk_2 UNIQUE (message);


--
-- Name: scores scores_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_pk PRIMARY KEY (score_id);


--
-- Name: Collisions table_name_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Collisions"
    ADD CONSTRAINT table_name_pk PRIMARY KEY (name);


--
-- Name: tips tips_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tips
    ADD CONSTRAINT tips_pk PRIMARY KEY (id);


--
-- Name: tips tips_pk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tips
    ADD CONSTRAINT tips_pk_2 UNIQUE (tips);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: users users_pk_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk_3 UNIQUE (username);


--
-- Name: scores scores_users_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

