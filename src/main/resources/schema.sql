DROP TABLE IF EXISTS tour_log CASCADE;
DROP TABLE IF EXISTS tour CASCADE;

CREATE TABLE tour (
                      id VARCHAR(36) PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      description TEXT,
                      from_location VARCHAR(100),
                      to_location VARCHAR(100),
                      transport_type VARCHAR(50),
                      distance DOUBLE PRECISION,
                      estimated_time DOUBLE PRECISION,
                      map_image_url VARCHAR(255)
);

CREATE TABLE tour_log (
                          log_id VARCHAR(36) PRIMARY KEY,
                          date TIMESTAMP,
                          time DOUBLE PRECISION,
                          comment TEXT,
                          difficulty DOUBLE PRECISION,
                          total_distance DOUBLE PRECISION,
                          total_time DOUBLE PRECISION,
                          rating DOUBLE PRECISION,
                          tour_id VARCHAR(36),
                          FOREIGN KEY (tour_id) REFERENCES tour (id) ON DELETE CASCADE
);
