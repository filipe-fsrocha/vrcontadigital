create table next_account (
    id varchar(36) primary key,
    next_account_number varchar(5) not null,
    last_updated timestamp not null
);

create table person (
    id varchar(36) primary key,
    cpf varchar(11) not null unique,
    name varchar(255) not null,
    account_id varchar(36) not null unique
);

create table account (
    id varchar(36) primary key,
    bank varchar(3) not null,
    account varchar(5) not null unique,
    card_number varchar(16) not null unique,
    password varchar(255) not null
);

create table balance (
    id varchar(36) primary key,
    account_id varchar(36) not null unique,
    balance decimal(10,2) not null,
    last_updated timestamp not null
);

create table key_pix (
    id varchar(36) primary key,
    _key varchar(100) not null unique,
    account_id varchar(36) not null,
    created_at timestamp not null
);

-- Init account number
insert into next_account (id, next_account_number, last_updated) values ('0edf91b8-d7c1-4e0f-8053-83fdd8947bd9', '00001', now());

-- Configure FK's
alter table person add foreign key (account_id) references account(id);
alter table balance add foreign key (account_id) references account(id);
alter table key_pix add foreign key (account_id) references account(id);