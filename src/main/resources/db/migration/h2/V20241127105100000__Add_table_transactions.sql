create table transactions (
    id varchar(36) primary key,
    account_id varchar(36) not null,
    amount decimal(10,2) not null,
    operation_type varchar(10) not null,
    created_at timestamp not null,
    status varchar(100) not null,
    foreign key (account_id) references account(id)
);

-- Load data for tests
INSERT INTO key_pix (id, `_key`, account_id, created_at) VALUES ('a445cbaa-58d9-475b-ba5a-3ffc308f9feb', '11111111111', '46f8acb7-f100-4749-9815-9afdd9c2afcd', '2024-11-26 20:53:54');