CREATE SCHEMA IF NOT EXISTS plan_schema;

CREATE TABLE plan_schema.data_plans (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    data_amount VARCHAR(50) NOT NULL,
    monthly_price DECIMAL(8,2) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE plan_schema.voice_options (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    call_minutes VARCHAR(50),
    monthly_price DECIMAL(8,2) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE plan_schema.overseas_calling_options (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    target_countries VARCHAR(1000),
    monthly_price DECIMAL(8,2) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO plan_schema.data_plans (id, name, data_amount, monthly_price, description) VALUES
('30gb-plan', '30GB', '30GB', 2970.00, 'データ通信量'),
('110gb-plan', '110GB 大盛り', '110GB', 4950.00, '大盛りオプション(+80GB/月)を含む');

INSERT INTO plan_schema.voice_options (id, name, call_minutes, monthly_price, description) VALUES
('voice-none', '申し込まない', '', 0.00, ''),
('voice-unlimited', '申し込む', 'unlimited', 2200.00, '2,200円/月');

INSERT INTO plan_schema.overseas_calling_options (id, name, target_countries, monthly_price, description) VALUES
('overseas-none', '申し込まない', '', 0.00, '1分/回無料'),
('overseas-unlimited', '申し込む', '', 1000.00, '1,000円/月');
