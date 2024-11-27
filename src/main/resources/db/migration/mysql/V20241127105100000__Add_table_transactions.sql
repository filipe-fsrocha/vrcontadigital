create table transactions (
    id varchar(36) primary key,
    account_id varchar(36) not null,
    amount decimal(10,2) not null,
    operation_type varchar(10) not null,
    created_at timestamp not null,
    status varchar(100) not null,
    foreign key (account_id) references account(id)
);
