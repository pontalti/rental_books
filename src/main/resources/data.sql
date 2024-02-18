drop table if exists BOOK cascade;
drop table if exists BOOK_TYPE cascade;
drop table if exists CUSTOMER cascade;
drop table if exists RENTAL cascade;
drop table if exists RENTAL_BOOK cascade;

create table BOOK (
	QUANTITY_AVAILABLE integer,
	QUANTITY_OWNED integer,
	BOOK_TYPE_ID bigint not null,
	ID bigint generated by default as identity,
	TITLE varchar(100),
	DESCRIPTION varchar(4000),
	IMAGE_URL varchar(4000),
	primary key (ID)
);

create table BOOK_TYPE (
	ID bigint generated by default as identity,
	TYPE varchar(50),
	DESCRIPTION varchar(1000),
	primary key (ID)
);

create table CUSTOMER (
	ID bigint generated by default as identity,
	NAME varchar(250),
	primary key (ID)
);

create table RENTAL (
	STATUS tinyint check (STATUS between 0 and 1),
	CUSTOMER_ID bigint,
	ID bigint generated by default as identity,
	primary key (ID)
);

create table RENTAL_BOOK (
	QUANTITY integer,
	BOOK_ID bigint,
	ID bigint generated by default as identity,
	RENTAL_ID bigint,
	primary key (ID)
);

create index INDEX_TITLE on BOOK (TITLE);
create index INDEX_DESCRIPTION on BOOK (DESCRIPTION);
create index INDEX_QUANTITY_OWNED on BOOK (QUANTITY_OWNED);
create index INDEX_QUANTITY_AVAILABLE on BOOK (QUANTITY_AVAILABLE);
create index INDEX_TYPE on BOOK_TYPE (TYPE);
create index INDEX_NAME on CUSTOMER (NAME);
create index INDEX_BOOK_ID on RENTAL_BOOK (BOOK_ID);
create index INDEX_QUANTITY on RENTAL_BOOK (QUANTITY);

alter table if exists BOOK 
   add constraint FK8hkxo88jpw7ghn1yrb3nonkxg 
   foreign key (BOOK_TYPE_ID) 
   references BOOK_TYPE;

alter table if exists RENTAL 
   add constraint FKolqbxa6c1tuban4s3hs0k2yws 
   foreign key (CUSTOMER_ID) 
   references CUSTOMER;

alter table if exists RENTAL_BOOK 
   add constraint FKlvep4as6d33wi41lfesunj7nf 
   foreign key (BOOK_ID) 
   references BOOK;

alter table if exists RENTAL_BOOK 
   add constraint FKkuuy1h58jutengnnhmjtbhv2p 
   foreign key (RENTAL_ID) 
   references RENTAL;

INSERT INTO CUSTOMER (NAME) VALUES ('Gustavo Pontalti');
INSERT INTO CUSTOMER (NAME) VALUES ('Edmundo Souza');

INSERT INTO BOOK_TYPE (TYPE, DESCRIPTION) VALUES ('Comic book', 'A comic book, also called comicbook, comic magazine or simply comic, is a publication that consists of comics art in the form of sequential juxtaposed panels that represent individual scenes. Panels are often accompanied by descriptive prose and written narrative, usually, dialogue contained in word balloons emblematic of the comics art form.');
INSERT INTO BOOK_TYPE (TYPE, DESCRIPTION) VALUES ('Drama', 'The drama genre is strongly based in a character, or characters, that are in conflict at a crucial moment in their lives. Most dramas revolve around families and often have tragic or painful resolutions.');
INSERT INTO BOOK_TYPE (TYPE, DESCRIPTION) VALUES ('Romance', 'A romance novel or romantic novel is a genre fiction novel that primary focuses on the relationship and romantic love between two people, typically with an emotionally satisfying and optimistic ending.');
INSERT INTO BOOK_TYPE (TYPE, DESCRIPTION) VALUES ('Fiction', 'Genre fiction, also known as formula fiction or popular fiction, is a term used in the book-trade for fictional works written with the intent of fitting into a specific literary genre in order to appeal to readers and fans already familiar with that genre.');

INSERT INTO BOOK (TITLE, QUANTITY_OWNED, QUANTITY_AVAILABLE, BOOK_TYPE_ID, DESCRIPTION, IMAGE_URL) VALUES ('Marvel - Spider-verse. Spider-Man Copertina rigida – 16 luglio 2020',1,1,1,'La più grande ragno-storia di tutti i tempi. Quando i crudeli Eredi iniziano a sterminare tutti gli Uomini e le Donne Ragno del Multiverso, solo un''alleanza tra ogni Spider-Man esistente potrà salvare la situazione e impedire che una nefasta profezia si realizzi. La saga da cui ha preso ispirazione il film di animazione vincitore di un premio Oscar "Spider-Man: Un nuovo universo".','https://m.media-amazon.com/images/I/51UycFNq2XL._SY445_SX342_.jpg');
INSERT INTO BOOK (TITLE, QUANTITY_OWNED, QUANTITY_AVAILABLE, BOOK_TYPE_ID, DESCRIPTION, IMAGE_URL) VALUES ('DC - Batman: The Dark Knight Returns 30th Anniversary Edition',1,1,1,'Writer/artist Frank Miller completely reinvents the legend of Batman in this saga of a near-future Gotham City gone to rot, 10 years after the Dark Knight’s retirement. Forced to take action, the Dark Knight returns in a blaze of fury, taking on a whole new generation of criminals and matching their level of violence. He is soon joined by a new Robin—a girl named Carrie Kelley, who proves to be just as invaluable as her predecessors. Can Batman and Robin deal with the threat posed by their deadliest enemies, after years of incarceration have turned them into perfect psychopaths? And more important, can anyone survive the coming fallout from an undeclared war between the superpowers—or the clash of what were once the world’s greatest heroes? Hailed as a comics masterpiece, THE DARK KNIGHT RETURNS is Frank Miller’s (300 and Sin City) reinvention of Gotham’s legendary protector. It remains one of the most influential stories ever told in comics, with its echoes felt in all media forms of DC’s storytelling. Collects issues #1-4.','https://m.media-amazon.com/images/I/917wsCEYSML._SY466_.jpg');
INSERT INTO BOOK (TITLE, QUANTITY_OWNED, QUANTITY_AVAILABLE, BOOK_TYPE_ID, DESCRIPTION, IMAGE_URL) VALUES ('Marvel - X-Men ''92. Genesi x-trema (Vol. 1)',1,1,1,'Tornano gli X-Men degli anni 90 in una versione per giovani lettori. È tempo di riaprire la Scuola Xavier per Giovani Dotati… e vivere nuove, emozionanti avventure. Il ritorno di classici nemici come Omega Red, i gemelli Fenris, Dracula e il Darkhold. Inoltre, una vacanza romantica di Scott Summers e Jean Grey non potrà che finire con una sinistra minaccia da combattere. Età di lettura: da 8 anni.','https://m.media-amazon.com/images/I/71stK1TRR4L._SY466_.jpg');
INSERT INTO BOOK (TITLE, QUANTITY_OWNED, QUANTITY_AVAILABLE, BOOK_TYPE_ID, DESCRIPTION, IMAGE_URL) VALUES ('Hellblazer - John Constantine, Hellblazer Vol. 1: Original Sins',1,1,1,'The very first Hellblazer collection ORIGINAL SINS is available in a new edition that includes John Constantine’s appearances in SWAMP THING. This is the first of a series of new HELLBLAZER editions starring Vertigo’s longest running antihero, John Constantine, England’s chain-smoking, low-rent magus. This first collection is a loosely connected series of tales of John’s early years where Constantine was at his best and at his worst, all at the same time.','https://m.media-amazon.com/images/I/91yHarNxozL._SY466_.jpg');
