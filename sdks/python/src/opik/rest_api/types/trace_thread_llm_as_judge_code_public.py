# This file was auto-generated by Fern from our API Definition.

import typing

import pydantic
import typing_extensions
from ..core.pydantic_utilities import IS_PYDANTIC_V2, UniversalBaseModel
from ..core.serialization import FieldMetadata
from .llm_as_judge_message_public import LlmAsJudgeMessagePublic
from .llm_as_judge_model_parameters_public import LlmAsJudgeModelParametersPublic
from .llm_as_judge_output_schema_public import LlmAsJudgeOutputSchemaPublic


class TraceThreadLlmAsJudgeCodePublic(UniversalBaseModel):
    model: LlmAsJudgeModelParametersPublic
    messages: typing.List[LlmAsJudgeMessagePublic]
    schema_: typing_extensions.Annotated[typing.List[LlmAsJudgeOutputSchemaPublic], FieldMetadata(alias="schema")]

    if IS_PYDANTIC_V2:
        model_config: typing.ClassVar[pydantic.ConfigDict] = pydantic.ConfigDict(extra="allow", frozen=True)  # type: ignore # Pydantic v2
    else:

        class Config:
            frozen = True
            smart_union = True
            extra = pydantic.Extra.allow
