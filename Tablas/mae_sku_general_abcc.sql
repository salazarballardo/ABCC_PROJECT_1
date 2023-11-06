create table mae_sku_general_abcc (sku int not null, articulo varchar(15) not null, marca varchar(15), modelo varchar(20), id_departamento smallint not null, id_clase smallint not null,
             id_familia smallint not null, stock int not null, cantidad int not null, descontinuado char(1) default '0', fechaalta timestamp default now(), fechabaja timestamp default '1900-01-01')
ALTER TABLE mae_sku_general_abcc ADD CONSTRAINT sku_unique UNIQUE (sku);			 