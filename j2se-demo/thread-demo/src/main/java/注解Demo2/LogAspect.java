package 注解Demo2;

import java.security.SignatureException;
/**
 * 日志保存拦截
 * @author azy
 *
 */
//@Component
//@Aspect
public class LogAspect //extends AdminBaseController{
{
/*	protected Logger logger = LoggerFactory.getLogger(LogAspect.class);
	@Resource
	private LogRecordService logRecordService;
	
	*//**
	 * 获取日志注解
	 * @param joinPointLogAnnotation
	 * @return
	 * @throws SignatureException
	 *//*
	private LogAnnotation getLogAnnotation(ProceedingJoinPoint joinPoint) throws SignatureException{
		Signature signature=joinPoint.getSignature();
		if(!(signature instanceof MethodSignature)){
			logger.warn("记录日志只支持Method注解！"+signature.getClass().getName());
			return null;
		}
		MethodSignature methodSignature=(MethodSignature) signature;
		LogAnnotation annotation=methodSignature.getMethod().getAnnotation(LogAnnotation.class);
		if(annotation==null){
			annotation=methodSignature.getMethod().getDeclaringClass().getAnnotation(LogAnnotation.class);
		}
		return annotation;
	}
	
	@SuppressWarnings("unchecked")
	@Around("@annotation(cn.swiftpass.core.client.controller.annotation.LogAnnotation)")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
		Object obj=null;
		LogAnnotation annotation=this.getLogAnnotation(joinPoint);
		if(annotation!=null){
			LogInfoDto logInfoDto=null;
			Exception exception=null;
			try {
				obj=joinPoint.proceed();
				if(obj instanceof ResponseJsonData){
					ResponseJsonData jsonData=(ResponseJsonData) obj;
					Object obj2=jsonData.getObj();
					if(obj2 instanceof LogInfoDto){
						logInfoDto=(LogInfoDto) obj2;
					}else if(obj2 instanceof Map){
						Map<Object, Object> objMap=(Map<Object, Object>) obj2;
						logInfoDto=(LogInfoDto) objMap.get("log");
					}
				}
			} catch (Exception e) {
				exception=e;
				logger.error("记录日志AOP处理异常！",e);
			}
			String content=null;
			if(logInfoDto!=null){
				content=logInfoDto.getContent();
			}else if(exception!=null){
				content=exception.getMessage();
			}
			LogModuleEnum moduleEnum=annotation.module();
			LogTypeEnum typeEnum=annotation.type();
			if(StringUtils.isBlank(content)){
				content=annotation.doContent();
			}
			try {
				String userName="system";
				UserDto userDto=getLoginUser();
				if(userDto!=null){
					userName=userDto.getUserName();
				}
				LogRecordDto logRecordDto=new LogRecordDto(userName, moduleEnum.getId(), moduleEnum.getName(), typeEnum.getId(), typeEnum.getName(), content, null);
				logRecordService.save(logRecordDto);
			} catch (Exception e) {
				logger.error("记录日志时异常！",e);
			}
			if(exception!=null){
				throw exception;
			}
		}else{
			obj=joinPoint.proceed();
		}
		return obj;
	}*/
}
