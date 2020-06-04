local key = KEYS[1]
local limit = tonumber(KEYS[2])
local length = tonumber(KEYS[3])
--redis.log(redis.LOG_NOTICE,' length: '..length)
local current = redis.call('GET', key)
if current == false then
   --redis.log(redis.LOG_NOTICE,key..' is nil ')
   redis.call('SET', key,1)
   redis.call('EXPIRE',key,length)
   --redis.log(redis.LOG_NOTICE,' set expire end')
   return '1'
else
   --redis.log(redis.LOG_NOTICE,key..' value: '..current)
   local num_current = tonumber(current)
   if num_current+1 > limit then
       return '0'
   else
       redis.call('INCRBY',key,1)
       return '1'
   end
end