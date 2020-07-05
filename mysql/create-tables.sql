DROP TABLE IF EXISTS clients_addl_info;
DROP TABLE IF EXISTS clients_authorized_grant_types;
DROP TABLE IF EXISTS clients_redirect_uri;
DROP TABLE IF EXISTS clients_resource_ids;
DROP TABLE IF EXISTS clients_scope;
DROP TABLE IF EXISTS clients_authorities;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS guardian_children;
DROP TABLE IF EXISTS guardian;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS tenants;
DROP TABLE IF EXISTS user_granted_authorities;
DROP TABLE IF EXISTS users;
CREATE TABLE clients (
  id varchar(255) NOT NULL,
  access_token_validity int DEFAULT NULL,
  auto_approve bit(1) DEFAULT NULL,
  client_id varchar(64) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  credentials_locked bit(1) NOT NULL,
  expired bit(1) NOT NULL,
  locked bit(1) NOT NULL,
  refresh_token_validity int DEFAULT NULL,
  tenant_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_clientId (client_id)
) ENGINE=InnoDB;
LOCK TABLES clients WRITE;
UNLOCK TABLES;

CREATE TABLE clients_addl_info (
  client_id varchar(255) NOT NULL,
  k varchar(255) DEFAULT NULL,
  v varchar(255) DEFAULT NULL,
  KEY clients_addl_info_pk (client_id),
  CONSTRAINT clients_addl_info_cid_fk FOREIGN KEY (client_id) REFERENCES clients (id)
) ENGINE=InnoDB;

CREATE TABLE clients_authorities (
  client_id varchar(255) NOT NULL,
  authorities tinyblob,
  KEY client_autho_client_id_pk (client_id),
  CONSTRAINT client_autho_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (id)
) ENGINE=InnoDB;

CREATE TABLE clients_authorized_grant_types (
  client_id varchar(255) NOT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  KEY clients_agt_client_id_pk (client_id),
  CONSTRAINT clients_agt_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (id)
) ENGINE=InnoDB;

CREATE TABLE clients_redirect_uri (
  client_id varchar(255) NOT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  KEY client_red_uri_client_id_pk (client_id),
  CONSTRAINT client_red_uri_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (id)
) ENGINE=InnoDB;

CREATE TABLE clients_resource_ids (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(255) DEFAULT NULL,
  KEY client_res_id_client_id_pk (client_id),
  CONSTRAINT client_res_id_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (id)
) ENGINE=InnoDB;

CREATE TABLE clients_scope (
  client_id varchar(255) NOT NULL,
  scope varchar(255) DEFAULT NULL,
  KEY client_scope_client_id_pk (client_id),
  CONSTRAINT client_scope_client_id_fk FOREIGN KEY (client_id) REFERENCES clients (id)
) ENGINE=InnoDB;

CREATE TABLE guardian (
  user_id varchar(255) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  tenant_id varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB;

CREATE TABLE guardian_children (
  guardian_id varchar(255) NOT NULL,
  children varchar(255) DEFAULT NULL,
  KEY guardian_children_id_pk (guardian_id),
  CONSTRAINT guardian_children_id_fk FOREIGN KEY (guardian_id) REFERENCES guardian (user_id)
) ENGINE=InnoDB;

CREATE TABLE students (
  user_id varchar(255) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  guardian_id varchar(255) DEFAULT NULL,
  tenant_id varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB;


CREATE TABLE tenants (
  id varchar(255) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  default_value bit(1) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  discriminator varchar(255) DEFAULT NULL,
  expiry int DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  refresh_expiry int DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_discriminator (discriminator),
  UNIQUE KEY uq_name (name)
) ENGINE=InnoDB;

CREATE TABLE users (
  id varchar(255) NOT NULL,
  created_at datetime DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  credentials_locked bit(1) NOT NULL,
  email varchar(255) DEFAULT NULL,
  expired bit(1) NOT NULL,
  fullname varchar(255) DEFAULT NULL,
  locked bit(1) NOT NULL,
  password varchar(255) DEFAULT NULL,
  tenant_id varchar(255) DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_email (tenant_id,email),
  UNIQUE KEY uq_username (tenant_id,username)
) ENGINE=InnoDB;

CREATE TABLE user_granted_authorities (
  user_id varchar(255) NOT NULL,
  granted_authorities varchar(255) DEFAULT NULL,
  KEY gser_granted_auth_user_id_pk (user_id),
  CONSTRAINT FKabh7nyetaaiin2lb4tokajj4c FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

