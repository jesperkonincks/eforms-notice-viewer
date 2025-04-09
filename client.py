import requests
import argparse
import sys

def convert_xml_to_html(xml_file, language, server_url):
    """
    Convert an XML notice file to HTML using the eforms-notice-viewer service
    
    Args:
        xml_file (str): Path to the XML file
        language (str): Two-letter language code (e.g., 'en', 'fr')
        server_url (str): URL of the eforms-notice-viewer service
    
    Returns:
        str: HTML content or error message
    """
    try:
        # Read XML file
        with open(xml_file, 'r', encoding='utf-8') as f:
            xml_content = f.read()
        
        # Send request to service
        response = requests.post(
            f"{server_url}/api/v1/convert",
            params={"language": language},
            data=xml_content,
            headers={"Content-Type": "application/xml"}
        )
        
        if response.status_code == 200:
            return response.text
        else:
            return f"Error: {response.status_code} - {response.text}"
            
    except FileNotFoundError:
        return f"Error: XML file not found: {xml_file}"
    except Exception as e:
        return f"Error: {str(e)}"

def main():
    parser = argparse.ArgumentParser(description='Convert eForms XML to HTML')
    parser.add_argument('xml_file', help='Path to the XML file')
    parser.add_argument('language', help='Two-letter language code (e.g., en, fr)')
    parser.add_argument('--server', default='http://localhost:8080',
                      help='eforms-notice-viewer server URL (default: http://localhost:8080)')
    parser.add_argument('--output', help='Output HTML file path (optional)')
    
    args = parser.parse_args()
    
    # Convert XML to HTML
    html_content = convert_xml_to_html(args.xml_file, args.language, args.server)
    
    # Output result
    if args.output:
        with open(args.output, 'w', encoding='utf-8') as f:
            f.write(html_content)
        print(f"HTML output written to: {args.output}")
    else:
        print(html_content)

if __name__ == "__main__":
    main()
