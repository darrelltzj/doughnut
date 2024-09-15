/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AnswerDTO } from '../models/AnswerDTO';
import type { AnsweredQuestion } from '../models/AnsweredQuestion';
import type { AssessmentAttempt } from '../models/AssessmentAttempt';
import type { AssessmentResult } from '../models/AssessmentResult';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class RestAssessmentControllerService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @param assessmentQuestionInstance
     * @param requestBody
     * @returns AnsweredQuestion OK
     * @throws ApiError
     */
    public answerQuestion(
        assessmentQuestionInstance: number,
        requestBody: AnswerDTO,
    ): CancelablePromise<AnsweredQuestion> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/api/assessment/{assessmentQuestionInstance}/answer',
            path: {
                'assessmentQuestionInstance': assessmentQuestionInstance,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                500: `Internal Server Error`,
            },
        });
    }
    /**
     * @param assessmentAttempt
     * @returns AssessmentResult OK
     * @throws ApiError
     */
    public submitAssessmentResult(
        assessmentAttempt: number,
    ): CancelablePromise<AssessmentResult> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/api/assessment/{assessmentAttempt}',
            path: {
                'assessmentAttempt': assessmentAttempt,
            },
            errors: {
                500: `Internal Server Error`,
            },
        });
    }
    /**
     * @param notebook
     * @returns AssessmentAttempt OK
     * @throws ApiError
     */
    public generateAssessmentQuestions(
        notebook: number,
    ): CancelablePromise<AssessmentAttempt> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/api/assessment/questions/{notebook}',
            path: {
                'notebook': notebook,
            },
            errors: {
                500: `Internal Server Error`,
            },
        });
    }
    /**
     * @returns AssessmentAttempt OK
     * @throws ApiError
     */
    public getMyAssessments(): CancelablePromise<Array<AssessmentAttempt>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/api/assessment',
            errors: {
                500: `Internal Server Error`,
            },
        });
    }
}
