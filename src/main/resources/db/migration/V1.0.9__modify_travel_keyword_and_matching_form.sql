-- 유니크 제약 조건 추가
ALTER TABLE travel_keyword
ADD CONSTRAINT unique_travel_keyword_matching_form UNIQUE (TRAVEL_KEYWORD, MATCHING_FORM_ID);

-- 컬럼 제거
ALTER TABLE travel_keyword
DROP COLUMN GROUP_SIZE,
DROP COLUMN COST;
