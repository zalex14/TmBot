--handset zalex14:1
-- Таблица информации о приюте
-- id приюта Long id
-- id telegram Long telegramId
-- название приюта String title
-- общая информация String information
-- адрес String address
-- географическая широта Float latitude
-- географическая долгота Float longitude
-- телефон String phone
-- режим работы String workingHours
-- правила пропуска на территорию String territoryAdmission
-- правила нахождения на территории String territoryStaying
-- контакты охраны для пропуска авто String securityContacts
-- правила общения с животным String petAcquaintance
-- список документов для усыновления питомца String documentList
-- рекомендации по транспортировке String travelRecommendation
-- рекомендации по обустройству для щеноков/котят String childArranging
-- рекомендации по обустройству для взрослыхе животных String adultArranging
-- рекомендации по обустройству для больных животных String sickArrangement
-- советы кинолога (только для собак и щенков) String handlerAdvice
-- возможные причины отказа в усыновлении String refusalReason
create table if not exists shelters
(
    id                    bigserial not null primary key,
    telegram_id           bigint,
    title                 varchar(255),
    information           varchar(2047),
    address               varchar(255),
    latitude              real,
    longitude             real,
    phone                 varchar(255),
    working_hours         varchar(255),
    territory_admission   varchar(2047),
    territory_staying     varchar(2047),
    security_contacts     varchar(255),
    pet_acquaintance      varchar(2047),
    documents             varchar(2047),
    travel_recommendation varchar(2047),
    child_arrangement     varchar(2047),
    adult_arrangement     varchar(2047),
    sick_arrangement      varchar(2047),
    handler_advice        varchar(2047),
    refusal_reason        varchar(2047)
);
create unique index if not exists shelters_idx ON shelters (id);

--handset zalex14:2
-- Таблица кинологов
-- id Long id
-- телеграм id Long telegramId
-- пользователь телеграм String telegramUserName
-- фио String fullName
-- организация String company
-- дополнительная информация (личные дипломы, достяжения кинолога) String info
create table if not exists handlers
(
    id                bigserial not null primary key,
    telegram_id       bigint,
    telegram_username varchar(255),
    full_name         varchar(255),
    company           varchar(255),
    info              varchar(2047)
);
create unique index if not exists handlers_idx ON handlers (id);

--handset zalex14:3
-- Таблица волонтеров
-- id Long id
-- телеграм id  Long telegramId
-- пользователь telegram String telegramUserName
-- фио String fullName
-- дополнительная информация String info
-- id приюта shelterId
create table if not exists volunteers
(
    id                bigserial not null primary key,
    telegram_id       bigint,
    telegram_username varchar(255),
    full_name         varchar(255),
    info              varchar(2047),
    shelter_id        bigint
);
create unique index if not exists volunteers_idx ON volunteers (id);

--handset zalex14:4
-- Таблица клиентов
-- id клиента Long id
-- фио String fullName
-- телеграм id Long telegramChatId
-- пользователь telegram String telegramUserName
-- усыновитель Boolean isAdopter
-- доп.информация comment

create table if not exists customers
(
    id                bigserial not null primary key,
    full_name         varchar(255),
    telegram_id       bigint,
    telegram_username varchar(255),
    adopter           boolean,
    comment           varchar(2047)
);

create unique index if not exists customers_idx ON customers (id);

-- changeset zalex14:5
-- Таблицы pets
-- id Long id
-- кличка String nickName
-- возраст int age
-- наличие заболевания boolean isSickness
-- описание питомца String description
-- порода (собака/кошка) boolean breed
-- доступеность для усыновления boolean isAvailable;
-- остаток адаптационного периода int duration
-- состояние адаптации Schema schema;
create table if not exists pets
(
    id          bigserial not null primary key,
    nick        varchar(255),
    age         int,
    sickness    boolean,
    description varchar(2047),
    breed       boolean,
    available   boolean,
    duration    int,
    schema      varchar(255)
);
create unique index if not exists pets_idx ON pets (id);

--handset zalex14:6
-- Таблица ежедневные отчеты о содержании питомцев
-- id Long id
-- дата и время отчета LocalDateTime reportTime
-- рацион питомца String diet
-- общее самочувствие String health
-- ежедневные изменения в поведении String behavior
-- id клиента Long customerId
-- id питомца  Long petId
-- id ежедневного фото питомца Long photoId
create table if not exists reports
(
    id          bigserial not null primary key,
    report_time timestamp,
    diet        varchar(2047),
    health      varchar(2047),
    behavior    varchar(2047),
    customer_id bigint,
    pet_id      bigint,
    photo       varchar(256),
    foreign key (pet_id) references pets (id),
    foreign key (customer_id) references customers (id)
);
create unique index if not exists reports_idx ON reports (id);

--handset zalex14:7
-- Таблица сообщений
-- id Long id
-- id чата в телеграм Long telegramId
-- id приюта  Long shelterId
-- сообщение String comment
-- дата и время отправки сообщения LocalDateTime messageTime
-- направлен ответ Boolean isReply
create table if not exists messages
(
    id           bigserial not null primary key,
    message_time timestamp,
    comment      varchar(2047),
    telegram_id  bigint,
    shelter_id   int,
    is_reply     boolean
);
create unique index if not exists messages_idx ON messages (id);

--handset zalex14:8
-- Таблица бота
-- id Long id
-- id питомца   Long petId
-- id клиента  Long customerId
create table if not exists schedule
(
    id          bigserial not null primary key,
    pet_id      bigint,
    customer_id bigint,
    foreign key (pet_id) references pets (id),
    foreign key (customer_id) references customers (id)
);
create unique index if not exists schedule_idx ON schedule (id);