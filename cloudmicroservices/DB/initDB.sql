
-- ----------------------------
--  Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

BEGIN;
INSERT INTO `oauth_client_details`
VALUES
	( 'c1', 'res1', '$2a$10$NlBC84MVb7F95EXYTXwLneXgCca6/GipyWR5NHm8K0203bSQMLpvm', 'ROLE_ADMIN,ROLE_USER,ROLE_API', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com', NULL, 7200, 259200, 259200, 'false' );
	
INSERT INTO `oauth_client_details`
VALUES
	( 'aiqiyi', 'aiqiyi', '$2a$10$cxJJxC0QTTTmxSUFGx4VueSJhRUx/TcQd9R9WEhLGUtj7BGaB2dXW', 'get_user_info,get_fanslist,server, amanyworkdisok', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://localhost:10102/clients/tokens/redirect', NULL, 7200, 259200, 259200, 'true' );
	
INSERT INTO `oauth_client_details`
VALUES
	( 'youku', 'youku', '$2a$10$cxJJxC0QTTTmxSUFGx4VueSJhRUx/TcQd9R9WEhLGUtj7BGaB2dXW', 'get_user_info,get_fanslist,server, amanyworkdisok', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://localhost:10102/clients/tokens/redirect', NULL, 7200, 259200, 259200, 'true' );
COMMIT;


Drop table  if exists oauth_code;
create table oauth_code (
  create_time timestamp default now(),
  code VARCHAR(255),
  authentication BLOB,
  index code_index (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;