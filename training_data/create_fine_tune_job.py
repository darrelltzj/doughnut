import openai
import os
openai.api_key = os.getenv("OPENAI_API_TOKEN")

openai.FineTuningJob.create(training_file="file-3AzmRwAeqrTjndzMRifdPdEz", model="gpt-3.5-turbo")
