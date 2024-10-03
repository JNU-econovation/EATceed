from fastapi import APIRouter

router = APIRouter(
    prefix="/v1/ai/food_image_analysis",
    tags=["음식 이미지 분석"]
)

# 음식 이미지 분석 API
@router.post("/test")
async def food_image_analysis_test():
    return {"success": "성공"}
