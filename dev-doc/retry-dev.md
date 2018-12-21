https://blog.csdn.net/u011116672/article/details/77823867

@Retryable(value = SQLDataException.class, backoff = @Backoff(value = 0L))